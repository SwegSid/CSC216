package queue;


public class JobQueue {

    private final PriorityQueue<Job> queue;

    public JobQueue() {
        queue = new PriorityQueue<>();
    }

    /** Adds a job to the queue. */
    public void insert(Job job) {
        queue.insert(job);
    }

    public void runHighestPriority() {
        if (queue.isEmpty()) {
            return;
        }
        Job job = queue.removeHighest();
        job.execute();
    }

    public int size() {
        return queue.size();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
