# UFP SMS PROVIDER Module

A SMS Provider Module implements the com.froso.ufp.modules.optional.sms.model.sms.ISMSProvider interface 
which defines one function:

	
    public SMSProviderResult sendSMS(String number, String message)
    
the returned object looks like this and contains relevant information that is stored as result for the
sms sending operation

	SMSProviderResult {
		private Boolean success = false;
		private String externalId;
		private String provider;
		private String status;
		private String statusMessage;
	}
		

A Sms Provider is then enabled by simply attaching the @EnableXXX annotation in application configuration

	@EnableSmsProviderSlack
	@EnableSmsProviderClickatell


and the sms messaging module needs to be enabled to make use of the providers:

	@EnableSMS
