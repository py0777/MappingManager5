package test;


public class ServiceHandler {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String className = null;		// FQCN
		
		String trId = "BB";
		
		if ("BB".equals(trId)) {
			className = "test.BB";
		} else if ("AA".equals(trId)) {
			className = "test.AA";
		}
		
		Class clazz = null;
		Object obj = null;
		IService service = null;

		try {
			clazz = Class.forName(className);

			obj = clazz.newInstance();
			
			service = (IService) obj;

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String result = service.service("1111");
		
		System.out.println(result);

	}

}
