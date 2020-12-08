import com.infor.daf.icp.CMException;
import com.infor.daf.icp.Connection;
import com.infor.daf.icp.distribution.v1.Distribution;
import com.infor.daf.icp.distribution.v1.SubmitJob;
import com.infor.daf.icp.internal.email.EmailMessage;

import java.io.IOException;

public class EmailTest {
    public static void main(String[] args) {
        String url = System.getenv("BASE_URL");
        String key = System.getenv("CONSUMER_KEY");
        String secret = System.getenv("CLIENT_SECRET");
        Connection connection = new Connection(url, key, secret, Connection.AuthenticationMode.OAUTH1);
        connection.setTenant(System.getenv("TENANT_ID"));
        connection.setUsername(System.getenv("CLIENT_USER"));

        try {
            connection.connect();
            SubmitJob submitJob = new SubmitJob();
            submitJob.input.add(new SubmitJob.ItemXqueryFile("/Oleg_Test[@Purpose = \"Email\"]"));
            SubmitJob.EmailTarget emailTarget = new SubmitJob.EmailTarget(
                    "oleg.yapparov@infor.com", "donald.duck@anothercompany.com", "Subject", "Message");
            submitJob.targets.add(emailTarget);
            SubmitJob.SubmitResult submitResult = Distribution.distribute(connection, submitJob);
            System.out.println("Submit result: " + submitResult.isSuccess());
        } catch (CMException | IOException e) {
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
