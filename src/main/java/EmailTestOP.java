import com.infor.daf.icp.CMException;
import com.infor.daf.icp.Connection;
import com.infor.daf.icp.distribution.v1.Distribution;
import com.infor.daf.icp.distribution.v1.SubmitJob;

import java.io.IOException;

public class EmailTestOP {
    public static void main(String[] args) {
        String url = "https://nlbavwidm1.infor.com:9443/ca";
        String user = "oleg.yapparov@infor.com";
        String password = "";
        Connection connection = new Connection(url, user, password, Connection.AuthenticationMode.BASIC);
        try {
            connection.connect();
            SubmitJob submitJob = new SubmitJob();
            submitJob.input.add(new SubmitJob.ItemXqueryFile("/Oleg_Test[@Name = \"Email\"]"));
            SubmitJob.EmailTarget emailTarget = new SubmitJob.EmailTarget();
            emailTarget.to = "oleg.yapparov@infor.com";
            emailTarget.from = "another.user@infor.com";
            emailTarget.subject = "Subject";
            emailTarget.body = "Message";
            submitJob.targets.add(emailTarget);
            SubmitJob.SubmitResult submitResult = Distribution.distribute(connection, submitJob);
            System.out.println("Submit result: " + submitResult.isSuccess());
        } catch (CMException | IOException e) {
            e.printStackTrace();
            System.out.println("Could not connect, error: " + e.getMessage());
        } finally {
            try {
                connection.disconnect();
            } catch (CMException e) {
                e.printStackTrace();
                System.out.println("Exception when disconnecting: " + e.getMessage());
            }
        }
    }
}
