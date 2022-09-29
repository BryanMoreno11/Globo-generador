
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Bryan
 */
public class Crear_globos {

    private File[] imagenes;
    private BufferedImage globo;

    public Crear_globos() {
        try {
            this.globo = ImageIO.read(new File("C:/Users/Steven/Documents/NetBeansProjects/Globo_Generador/src/Recursos/z.png"));
        } catch (IOException ex) {
            Logger.getLogger(Crear_globos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public Crear_globos(File globoRuta) {
        try {
            this.globo = ImageIO.read(globoRuta);
        } catch (IOException ex) {
            Logger.getLogger(Crear_globos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public File[] cargarImagen() {
        JFileChooser selector = new JFileChooser();
        selector.setMultiSelectionEnabled(true);
        selector.setDialogTitle("Seleccione imágenes");
        FileNameExtensionFilter extension = new FileNameExtensionFilter("JPG & GIF & BMP & JPEG & WEBP & JFIF & PNG", "jpg", "gif", "bmp", "jpeg", "webp", "jfif", "png");
        selector.setFileFilter(extension);
        int flag = selector.showOpenDialog(null);
        if (flag == JFileChooser.APPROVE_OPTION) {
            imagenes = selector.getSelectedFiles();
        } 
        return imagenes;
    }

    public BufferedImage[] agregarGlobo(File[] imagenes) {
        BufferedImage[] globos = new BufferedImage[imagenes.length];
        BufferedImage auxGlobo = this.globo;
        BufferedImage dibuja_globo;
        BufferedImage img;
        try {
            for (int i = 0; i < imagenes.length; i++) {
                globo = auxGlobo;
                img = ImageIO.read(imagenes[i]);
                if (globo.getWidth() > img.getWidth()) {
                    img = ajustarImagen(img, globo.getWidth());
                    dibuja_globo = new BufferedImage(globo.getWidth(), img.getHeight() + globo.getHeight(), BufferedImage.TYPE_INT_RGB);
                } else {
                    globo = ajustarImagen(globo, img.getWidth());
                    dibuja_globo = new BufferedImage(img.getWidth(), img.getHeight() + globo.getHeight(), BufferedImage.TYPE_INT_RGB);
                }
                Graphics2D globod = dibuja_globo.createGraphics();
                globod.drawImage(img, 0, globo.getHeight(), null);
                globod.drawImage(globo, 0, 0, null);
                globos[i] = dibuja_globo;
            }
        } catch (IOException ex) {
            Logger.getLogger(Crear_globos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return globos;
    }

    public BufferedImage ajustarImagen(BufferedImage img, int width) {
        int imgAltura = img.getHeight();
        int relacionAnchoAltura = img.getWidth() / imgAltura;
        if (width / imgAltura > relacionAnchoAltura && relacionAnchoAltura > 0) {
            imgAltura = width / relacionAnchoAltura;
        }
        BufferedImage resizedimage = new BufferedImage(width, imgAltura, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedimage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(img, 0, 0, width, imgAltura, null);
        g2.dispose();
        return resizedimage;
    }

    public void guardarImagen(BufferedImage[] globos) {
        File folder;
        File ruta;
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Seleccione la carpeta de guardado");
        selector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        selector.showOpenDialog(null);
        folder = selector.getSelectedFile();
        try {
            for (int i = 0; i < globos.length; i++) {
                ruta = new File(folder.toString() + "/" + imagenes[i].getName());
                ImageIO.write(globos[i], "png", ruta);

            }
        } catch (IOException ex) {
            Logger.getLogger(Crear_globos.class.getName()).log(Level.SEVERE, null, ex);
        }

        JOptionPane.showMessageDialog(null, "Se han creado los globos correctamente!");

    }
}
