# UFP Core
 
 # unreleased
 
 # 1.0.6 unreleased
 
    - rest exception handling remove double output of messages
    - reset exception output message
    - sms auth controller protected authservice

 
 # 1.0.x releases
 
    - bugfixes and ci pipelines optimisation
 
 # 1.0.0 ufp release
 
 - the backend is released as is, final touch was to include static files as root to provide an application entry point
 
 - the as is state is providing ufp api for talking with the backend utility mongo services and controller the authentication functionality sms providers email, template functionality
    
    the state of the code base is based on many autowiring components the next work is to identify overcomplex contructor autowired components and shift to coding guidelines only to use constructor based autowiring
    
    this is a work in progress an continues in the 1.0 branch
   
 # 0.0.5 unreleased
  - resource extraction of link elements include sub-objects now
  - media handling, validate function in fileservice
  - breaking changes, all constructors shall receive their autowired dependencies now, coding style from spring team 
 
 # 0.0.4 unreleased
 
  - provide filesystem base class to resort to filesystem for persisten files - getting rid of additional server
  dependency for configuring the ftp, it is advised to handle this using remote volumes in the runnin stacks kubernetes/docker... 
 
 
 # 0.0.3 unreleased
 
  - set default coreuser on creation in metadata
  - straighten apidoc annotations
  - clean up maven dependencies, remove uneeded validators,test frameworks spock framework should be the only one used, junit dependency comes in through spring boot deps