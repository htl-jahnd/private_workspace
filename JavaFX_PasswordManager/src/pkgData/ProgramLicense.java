package pkgData;

public class ProgramLicense
{

    private String programName;
    private String license;
    private String additionalInformation;

    public ProgramLicense(String programName, String license, String additionalInformation)
    {
	super();
	this.programName = programName;
	this.license = license;
	this.additionalInformation = additionalInformation;
    }

    @Override
    public String toString()
    {
	return "ProgramLicense [programName=" + programName + ", license=" + license + ", additionalInformation="
		+ additionalInformation + "]";
    }

    public String getProgramName()
    {
	return programName;
    }

    public void setProgramName(String programName)
    {
	this.programName = programName;
    }

    public String getLicense()
    {
	return license;
    }

    public void setLicense(String license)
    {
	this.license = license;
    }

    public String getAdditionalInformation()
    {
	return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation)
    {
	this.additionalInformation = additionalInformation;
    }

}
