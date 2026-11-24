import java.util.*;

public class Heuristics {

    //first come first served.
    public Request fcfs(List<Request> requests) {
        if (requests.isEmpty()){
            return null;
        }

        Request first = requests.get(0);

        for (Request req : requests) {
            if (req.compareTo(first) < 0){ 
                first = req;
            }
        }
        return first;
    }

    // Shortest seek time first. Ties in distance go to the earlier arrival.
    public Request sstf(List<Request> requests, int currTrack) {
        Request closest = null;
        int bestDist = Integer.MAX_VALUE;

        for (Request req : requests) {
            int dist = Math.abs(currTrack - req.getTrack());
            if (dist < bestDist || (dist == bestDist && req.compareTo(closest) < 0)) {
                bestDist = dist;
                closest = req;
            }
        }
        return closest;
    }

    // LOOK
    // Returns null if nothing in that direction
    // Ties on the same track go to the earlier arrival.
    public Request look(List<Request> requests, int currTrack, boolean goingLeft) {
        Request best = null;

        for (Request req : requests) {
            int track = req.getTrack();
            if (goingLeft) {
                if (track <= currTrack && (best == null || track > best.getTrack() || (track == best.getTrack() && req.compareTo(best) < 0))) {
                    best = req;
                }
            } 
            else {
                if (track >= currTrack && (best == null || track < best.getTrack() || (track == best.getTrack() && req.compareTo(best) < 0))) {
                    best = req;
                }
            }
        }
        return best;
    }

    // C-LOOK
    // Ties on the same track go to the earlier arrival.
    public Request cLook(List<Request> requests, int currTrack) {
        Request ahead = null;
        Request min = null;
        for (Request req : requests) {
            int track = req.getTrack();
            if (track >= currTrack && (ahead == null || track < ahead.getTrack() || (track == ahead.getTrack() && req.compareTo(ahead) < 0))) {
                ahead = req;
            }
            if (min == null || track < min.getTrack() || (track == min.getTrack() && req.compareTo(min) < 0)) {
                min = req;
            }
        }
        if(ahead != null){
            return ahead;
        }
        else{
            return min;
        }
    }

    // Assumes ALL requests are passed in as pending
    // and the caller removes each served request before the next call.
    public Request optimal(List<Request> requests, int currTrack) {
        if (requests.isEmpty()) return null;

        int min = Integer.MAX_VALUE; 
        int max = Integer.MIN_VALUE;
        for (Request req : requests) {
            min = Math.min(min, req.getTrack());
            max = Math.max(max, req.getTrack());
        }

        // go to the nearer end first
        boolean goingLeft = (currTrack - min) <= (max - currTrack);

        Request next = look(requests, currTrack, goingLeft);
        if (next == null) {
            next = look(requests, currTrack, !goingLeft);
        }
        return next;
    }
}