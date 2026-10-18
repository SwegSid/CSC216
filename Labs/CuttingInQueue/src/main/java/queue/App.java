package queue;

/**
 * Inserts jobs in a jumbled order, then runs them all to show that they come
 * out highest-priority-first regardless of insertion order.
 */
public class App {

    public static void main(String[] args) {
        JobQueue jobQueue = new JobQueue();

        jobQueue.insert(new Job("Send email", 3));
        jobQueue.insert(new Job("Back up database", 10));
        jobQueue.insert(new Job("Print report", 1));
        jobQueue.insert(new Job("Deploy release", 7));

        System.out.println("Queued " + jobQueue.size() + " jobs. Running them now:\n");

        while (!jobQueue.isEmpty()) {
            jobQueue.runHighestPriority();
        }
    }
}
