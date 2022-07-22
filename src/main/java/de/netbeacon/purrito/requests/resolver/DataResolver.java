package de.netbeacon.purrito.requests.resolver;

import de.hypercdn.commons.imp.execution.action.helper.SupplierExecutionAction;
import de.hypercdn.commons.imp.execution.misc.exception.ExecutionException;
import okhttp3.OkHttpClient;

import java.util.concurrent.Executor;

public class DataResolver{

	private final OkHttpClient okHttpClient;
	private final Executor executor;

	public DataResolver(OkHttpClient okHttpClient, Executor callbackExecutor){
		this.okHttpClient = okHttpClient;
		this.executor = callbackExecutor;
	}

	public <T extends ResolvableData> SupplierExecutionAction<T> newResolveTask(DataRequest<T> dataRequest){
		var supplierExecutionAction = new SupplierExecutionAction<>(() -> {
			try{
				var response = okHttpClient.newCall(dataRequest.getRequest()).execute();
				return dataRequest.resolveData(response);
			}
			catch(Exception e){
				throw new ExecutionException(e);
			}
		});
		supplierExecutionAction.setExecutor(executor);
		return supplierExecutionAction;
	}

}
