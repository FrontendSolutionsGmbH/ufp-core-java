# UFP SLACK SMS PROVIDER Module

the slack provider module implements the ISMSprovider interface but sends the sms data to a slack chat channel

Enable the SlackSMS Provider by attaching the

	@EnableSmsProviderSlack
	
to your application configuration, the webhook is configured using the application.yml

	ufp.modules.sms.provider.slack.webhook
