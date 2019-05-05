package mm.common;

import nexcore.framework.core.data.IDataSet;
/*
 * 서비스 inteface 모든 서비스는 service라는 메소드를 가지며
 * 해당 서비스안에서 비즈니스처리를 함.
 * */
public interface IService {
	
	//IDataSet service(IDataSet arg) throws Exception;
	
	IDataSet service( IContextHeader ich, IDataSet arg ) throws Exception;

}
