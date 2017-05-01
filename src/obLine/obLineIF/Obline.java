package obLine.obLineIF;

public interface Obline {

    /**
     * This method is responsible to return the object code of line according to
     * its type which are Header , Text , End.
     * 
     * @return the full line that represent the record
     */
    public String getLine();

}
