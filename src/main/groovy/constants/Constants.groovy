package constants

class Constants {

    //MongoDB
    public static final int SERVER_PORT = 8080
    public static final String COLLECTION_NAME = "products"
    public static final String URI_MONGODB = "mongodb://localhost:27017"
    public  static final String DB_NAME = "test"
    public static final String MONGO_SAVE = "save"
    public static final String MONGO_DELETE = "delete"
    public static final String MONGO_UPDATE = "upadate"

    //API Respone
    public static final String MESSAGE_FAIL = "fail!!"
    public static final String MESSAGE_SUCCESS = "success!!"
    public static final int STATUS_OK = 200
    public static final int STATUS_FAIL = 500
    public static final String APPLICATION_JSON = "application/json"
    public static final String CONTENT_TYPE = "Content-Type"

    //AticeMQ
    public static final String ACTIVE_MY_QUEUE = "MyQueue"
    public static final String ACTIVE_MQ_USERNAME = "admin"
    public static final String ACTIVE_MQ_PASSWORD = "admin"
    public static final String ACTIVE_MQ_URL = "tcp://localhost:61616"
    public static final int ACTIVE_MQ_MAXCONNECTION = 10
    public static final int ACTIVE_MQ_TIME_OUT = 1000
}
