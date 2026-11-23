public class Request implements Comparable<Request>{
    private final int track;
    private final int arrivalTime; //time is in 1 track movement i.e. how long it takes the arm to move from track 1 to track 2
    private int finishTime = -1;

    /**
     * Constructor of Request
     * @param track the track the request is on
     * @param arrivalTime when the request arrived
     */
    public Request(int track, int arrivalTime) {
        this.track = track;
        this.arrivalTime = arrivalTime;
    }

    public int getTrack(){ 
        return track; 
    }

    public int getArrivalTime(){ 
        return arrivalTime; 
    }

    public int getFinishTime(){ 
        return finishTime; 
    }

    public void setFinishTime(int finishTime){
        this.finishTime = finishTime;
    }

    public int getWaitTime(){
        return finishTime - arrivalTime;
    }


    @Override
    public int compareTo(Request other) {
        return Integer.compare(this.arrivalTime, other.arrivalTime);
    }
}