package Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectStream {

    public Object ReadObject(String FileLocation)  {
        try {


        FileInputStream Input = new FileInputStream(FileLocation);
        ObjectInputStream ObjectReader = new ObjectInputStream(Input);
        Object Obj = (Object) ObjectReader.readObject();
        ObjectReader.close();
        return Obj;
        }
    catch(Exception e){
        System.out.println(e.getMessage());
            return null;
            
        }
    }

    public void WriteObject(String FileLocation, Object ObjectToWrite) {
        try{
            
            
        File file = new File(FileLocation);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream Output = new FileOutputStream(FileLocation);
        ObjectOutputStream ObjectWriter = new ObjectOutputStream(Output);
        ObjectWriter.writeObject(ObjectToWrite);
        ObjectWriter.close();
        }catch(Exception e){
            
            System.out.println(e.getMessage());
        }
    }
}
