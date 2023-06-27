public class MySql extends SQLDATA {

    @Override
    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public void setUser(String user) {
       this.user = user;
    }

    @Override
    public void setDBname(String dbname) {
        this.dbname = dbname;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUser() {
        return this.user;
    }

    @Override
    public String getPassword() {
        return this.password;
    }
    
}
