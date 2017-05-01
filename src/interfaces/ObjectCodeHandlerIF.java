package interfaces;

public interface ObjectCodeHandlerIF {

    /**
     * This method writes the Object code inside the file whose name is given as
     * parameter to the method.
     * 
     * @param fileDirectory:
     *            the name of the file where the object code will be written.
     * @throws Exception
     * 
     * 
     */
    public void writeFile(String fileDirectory);

}
