package com.pseudonova.saverod.persistentdatatypes;

import com.pseudonova.saverod.models.RodInstance;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import java.io.*;

public class RodInstanceType implements PersistentDataType<byte[], RodInstance> {

    @Override
    public Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Override
    public Class<RodInstance> getComplexType() {
        return RodInstance.class;
    }

    @Override
    public byte[] toPrimitive(RodInstance instance, PersistentDataAdapterContext context) {

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(out);
            os.writeObject(instance);
            return out.toByteArray();
        }catch (Exception ex){
            System.out.println("koekje");
            return null;
        }
    }

    @Override
    public RodInstance fromPrimitive(byte[] bytes, PersistentDataAdapterContext context) {
        try {
            return (RodInstance) convertFromBytes( bytes);
        } catch (Exception exception) {
            System.out.println("b");
           // exception.printStackTrace();
            return null;
        }
    }

    private Object convertFromBytes(byte[] bytes) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             ObjectInputStream in = new ObjectInputStream(bis)) {
            return in.readObject();
        }
    }

    private byte[] convertToBytes(Object object) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(object);
            return bos.toByteArray();
        }
    }


}
