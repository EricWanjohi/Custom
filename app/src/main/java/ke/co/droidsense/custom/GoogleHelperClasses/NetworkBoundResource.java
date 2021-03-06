package ke.co.droidsense.custom.GoogleHelperClasses;

import android.os.AsyncTask;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 * <p>
 * <p>
 * You can read more about it in the [Architecture
 * Guide](https://developer.android.com/arch).
 *
 * @param <ResultType>
 * @param <RequestType>
 */

public abstract class NetworkBoundResource<ResultType, RequestType> {
    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    public NetworkBoundResource() {
        result.setValue( Resource.loading( null ) );
        final LiveData<ResultType> dbSource = loadFromDb();
        result.addSource( dbSource, new Observer<ResultType>() {
            @Override
            public void onChanged(@Nullable ResultType data) {
                result.removeSource( dbSource );
                if (NetworkBoundResource.this.shouldFetch( data )) {
                    NetworkBoundResource.this.fetchFromNetwork( dbSource );
                } else {
                    result.addSource( dbSource, new Observer<ResultType>() {
                        @Override
                        public void onChanged(@Nullable ResultType newData) {
                            result.setValue( Resource.success( newData ) );
                        }
                    } );
                }
            }
        } );
    }

    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        result.addSource( dbSource, new Observer<ResultType>() {
            @Override
            public void onChanged(@Nullable ResultType newData) {
                result.setValue( Resource.loading( newData ) );
            }
        } );
        createCall().enqueue( new Callback<RequestType>() {
            @Override
            public void onResponse(Call<RequestType> call, Response<RequestType> response) {
                result.removeSource( dbSource );
                saveResultAndReInit( response.body() );
            }

            @Override
            public void onFailure(Call<RequestType> call, final Throwable t) {
                onFetchFailed();
                result.removeSource( dbSource );
                result.addSource( dbSource, new Observer<ResultType>() {
                    @Override
                    public void onChanged(@Nullable ResultType newData) {
                        result.setValue( Resource.error( t.getMessage(), newData ) );
                    }
                } );
            }
        } );
    }

    @MainThread
    private void saveResultAndReInit(final RequestType response) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                saveCallResult( response );
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                result.addSource( loadFromDb(), new Observer<ResultType>() {
                    @Override
                    public void onChanged(@Nullable ResultType newData) {
                        result.setValue( Resource.success( newData ) );
                    }
                } );
            }
        }.execute();
    }

    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    @MainThread
    protected boolean shouldFetch(@Nullable ResultType data) {
        return true;
    }

    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    @NonNull
    @MainThread
    protected abstract Call<RequestType> createCall();

    @MainThread
    protected void onFetchFailed() {
    }

    public final LiveData<Resource<ResultType>> getAsLiveData() {
        return result;
    }
}

