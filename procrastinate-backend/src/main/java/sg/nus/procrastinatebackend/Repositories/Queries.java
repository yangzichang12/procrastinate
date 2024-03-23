package sg.nus.procrastinatebackend.Repositories;

public interface Queries {

    public static final String SQL_INSERT_UPLOAD = "insert into procrastinate_uploads (upload_id,content_url,content_type,username) values (?,?,?,?)";
    
}
