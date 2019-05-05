package mm.common;

public class IContextHeader {

	private static final long serialVersionUID = 7488533948874957455L;
	  

	  private String trid = null;
	  
	  private String fnCd = null;
	  
	  
	  public String getTrId()
	  {
	    return trid;
	  }
	  public void setTrId(String trid)
	  {
	    this.trid = trid;
	  }
	  
	  public String getFnCd()
	  {
	    return fnCd;
	  }
	  public void setFnCd(String fnCd)
	  {
	    this.fnCd = fnCd;
	  }
}
