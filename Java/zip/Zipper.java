import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Zipper {
	
    private static final int ZIP_SERIALIZATION_BUFFERSIZE = 1024;

	/**
	 * Creates a ZIP from the provided data as String
	 * 
	 * @param s
	 *            String containing the data which should be zipped
	 * @author sgross8
	 * @return ByteArray representing the final ZIP file
	 */
	public static byte[] zip(String s) throws IOException {
	    Deflater compressor = new Deflater();
	    ByteArrayOutputStream boas = new ByteArrayOutputStream();
	    try {
	        compressor.setInput(s.getBytes("UTF8"));
	        compressor.finish();
	        byte[] buf = new byte[ZIP_SERIALIZATION_BUFFERSIZE];
	        while (!compressor.finished()) {
	            int count = compressor.deflate(buf);
	            boas.write(buf, 0, count);
	        }
	    } finally {
	        compressor.end();
	    }
	    return boas.toByteArray();
	}

	/**
	 * Reads the contetn of a ZIP File and returns it as a String
	 * 
	 * @param b
	 *            Byte-Array containing the data to be unzipped
	 * @author sgross8
	 * @return String containing the unzipped data
	 */
	public static String unzip(byte[] b) throws IOException {
	    Inflater decompressor = new Inflater();
	    try {
	        decompressor.setInput((byte[]) b);
	        ByteArrayOutputStream boas = new ByteArrayOutputStream();
	        byte[] buf = new byte[ZIP_SERIALIZATION_BUFFERSIZE];
	        while (!decompressor.finished()) {
	            int count = decompressor.inflate(buf);
	            boas.write(buf, 0, count);
	        }
	        decompressor.end();
	        return new String(boas.toByteArray(), "UTF-8");
	    } catch (DataFormatException e) {
	        throw new IOException(e);
	    } finally {
	        decompressor.end();
	    }
	}
    
    /**
     * Creates a ZIP containing the files provided
     * @param files files that should be zipped<p />
     * 			Map<filename, filecontent>
     * @author sgross8
     * @return ByteArray representing the final ZIP file
     */
	public static byte[] zip(Map<String, String> files) {
	    ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    byte[] buf = new byte[ZIP_SERIALIZATION_BUFFERSIZE];
	    try {
	        ZipOutputStream out = new ZipOutputStream(bos);
	        for(Map.Entry<String, String> f : files.entrySet()) {
	        	ByteArrayInputStream bis = new ByteArrayInputStream(f.getValue().getBytes(Charset.forName("UTF-8")));
	            out.putNextEntry(new ZipEntry(f.getKey()));

	            int len;
	            while((len = bis.read(buf)) > 0) {
	                out.write(buf, 0, len);
	            }
	            out.closeEntry();
	            bis.close();
	        }
	        out.close();
	        return bos.toByteArray();
	    } catch (IOException ex) {
	        logger.error("Error zipping files: " + ex.getMessage());
	    }
	    return null;
	}
    
    /**
     * Unzips a ZIP file returning the original files
     * @param zipFile ZIP Archive to be unzipped as a byte Array
     * @author sgross8
     * @return Map<filename, filecontent> containing the read files
     */
	public static Map<String, String> unZipAll(byte[] zipFile) {
		Map<String, String> files = new HashMap<String, String>();
		byte[] buffer = new byte[ZIP_SERIALIZATION_BUFFERSIZE];
	
		try {
			ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(zipFile));
			ZipEntry ze = zis.getNextEntry();
	
			while (ze != null) {
				String fileName = ze.getName();
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
	
				int len;
				while ((len = zis.read(buffer)) > 0) {
					bos.write(buffer, 0, len);
				}
	
				files.put(fileName, bos.toByteArray().toString());
	
				bos.close();
				ze = zis.getNextEntry();
			}
	
			zis.closeEntry();
			zis.close();
	
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return files;
	}  
}
