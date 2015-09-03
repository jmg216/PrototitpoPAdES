/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isa.prototipo;

import com.isa.firma.PAdESFirma;
import com.isa.plataform.KeyStoreValidator;
import com.isa.security.ISCertSecurityManager;
import com.isa.token.HandlerToken;
import com.isa.token.Token;
import com.isa.utiles.IdGenerator;
import com.isa.utiles.StreamDataSource;
import com.isa.utiles.Utiles;
import com.isa.utiles.UtilesResources;
import com.isa.utiles.UtilesWS;
import com.isa.ws.STR;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.security.BouncyCastleDigest;
import com.itextpdf.text.pdf.security.ExternalDigest;
import com.itextpdf.text.pdf.security.ExternalSignature;
import com.itextpdf.text.pdf.security.MakeSignature;
import com.itextpdf.text.pdf.security.MakeSignature.CryptoStandard;
import com.itextpdf.text.pdf.security.PrivateKeySignature;
import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import netscape.javascript.JSObject;

/**
 *
 * @author JMiraballes
 */
public class PrototipoPAdES extends javax.swing.JApplet {

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private HandlerToken handler;
    private HashMap certs;
    private HashMap aliasHash;
    private KeyStore keystore;  
    private String certIndex;
    private String password;
    private String cedula;
    private Integer solnumero;
    private Integer anio;
    
    /**
     * Initializes the applet PrototipoPAdES
     */
    @Override
    public void init() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PrototipoPAdES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PrototipoPAdES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PrototipoPAdES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrototipoPAdES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the applet */
        try {
            SecurityManager sm = new ISCertSecurityManager();
            System.setSecurityManager(sm);
            initComponents();
            initAppletComponents();
            initAliasHashCerts();
            KeyStoreValidator.setInitStoreValidator();
            UtilesResources.setRutaProperties(getParameter("ruta"));
            cedula = getParameter("cedula");
//          UtilesResources.setRutaProperties("http://localhost:8080/ISCertDemo1/resources/hacienda/applet.properties");
//          cedula = "2015";
//          setSize(480, 520);            
            sincronizarTokens();

            if (!handler.isTokenActivo()){
                msjLogin.setText("Debe conectar un token.");
                msjLogin.setVisible(true);
            }


        } catch (IOException ex) {
            Logger.getLogger(PrototipoPAdES.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void initAppletComponents(){
        mensaje.setVisible(false);
        msjLogin.setVisible(false);
        botonFirmar.setVisible(false);
    }
    
    private void initAliasHashCerts(){
        certs = new HashMap();
        aliasHash = new HashMap();        
    }    
    
    private void cargarCertificados(String password) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException{
        Token token = handler.getTokenActivo();
        token.login( password );
        token.obtenerCertificados();
        keystore = token.getKeystore();
        
        Enumeration enumer = keystore.aliases();
        for (; enumer.hasMoreElements(); ) {
            String alias = (String) enumer.nextElement();
            aliasHash.put(String.valueOf(aliasHash.size()), alias);
        }
        
        ArrayList<String[]> elementos = new ArrayList();
        
        for (X509Certificate c : token.getListaCerts()){
            
            String fecha = Utiles.DATE_FORMAT_MIN.format(c.getNotBefore()) + "-" + Utiles.DATE_FORMAT_MIN.format(c.getNotAfter());
            String [] elem = new String [] { Utiles.getCN(c.getSubjectDN().getName()), Utiles.getCN(c.getIssuerDN().getName()), fecha };
            elementos.add( elem );
            certs.put(String.valueOf(certs.size()), c);
            
            //Inicializo el modelo de la lista que despliega los certificados e inserto los mismos
            MyTableModel modelo = new MyTableModel();
            modelo.addColumn("Nombre");
            modelo.addColumn("Emisor");
            modelo.addColumn("Fecha de validez");
            
            for( int i = 0; i < elementos.size(); i++){
                    modelo.addRow(elementos.get(i));
            }
            lista.setModel( modelo ); 
        }        
    }

    public HandlerToken getHandler() {
        return handler;
    }

    public void setHandler(HandlerToken handler) {
        this.handler = handler;
    }
    
    private void sincronizarTokens() throws IOException{
        handler = new HandlerToken();
        if (handler.isTokenActivo()){
            KeyStoreValidator.setKeystore(KeyStoreValidator.KEYSTORE_TOKEN);
        }
    } 
    
    /**
     * This method is called from within the init() method to initialize the
     * form. WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jFileChooser1 = new javax.swing.JFileChooser();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lista = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        msjLogin = new javax.swing.JLabel();
        mensaje = new javax.swing.JLabel();
        botonFirmar = new javax.swing.JButton();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Certificados"));

        lista.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Emisor", "Fecha Validez"
            }
        ));
        lista.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(lista);
        lista.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 450, -1));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Autenticación Token"));

        jLabel3.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel3.setText("Si desea ver los certificados del token ingrese el pin.");

        jPasswordField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPasswordField1ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel4.setText("Pin:");

        jButton5.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jButton5.setText("Aceptar");
        jButton5.setPreferredSize(new java.awt.Dimension(84, 20));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        msjLogin.setText("Mensaje");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addComponent(jLabel3)
                .addContainerGap(88, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(133, 133, 133))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(msjLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(122, 122, 122))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(176, 176, 176))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(msjLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 450, 150));

        mensaje.setText("Mensaje");
        jPanel1.add(mensaje, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 430, 25));

        botonFirmar.setText("Firmar");
        botonFirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonFirmarActionPerformed(evt);
            }
        });
        jPanel1.add(botonFirmar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 340, 110, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    /*
    private void mensajeError(){
        System.out.println("Error firmado PDF.");
        mensaje.setText("Error firmando documento!!!");
        mensaje.setForeground(Color.RED);
        mensaje.setVisible(true);       
    }
    
    private void mensajeError(String msg ){
        System.out.println("Error firmado PDF.");
        mensaje.setText(msg);
        mensaje.setForeground(Color.RED);
        mensaje.setVisible(true);       
    }    
    
    private void mensajeOK(){
        mensaje.setForeground(Color.black);
        mensaje.setText("Documento firmando.");
        mensaje.setVisible(true); 
    }*/
    
    private void mostrarMensaje( String msj){
        mensaje.setForeground(Color.BLACK);
        mensaje.setText(msj);
        mensaje.setVisible(true);
    }
    
    private void ocultarMensaje(){
        mensaje.setVisible(false);
    }
    
    private void seleccionarCertificado(){
        certIndex = String.valueOf( lista.getSelectedRow() );
    }
    
    private void jPasswordField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPasswordField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPasswordField1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        try {
            String pass = new String(jPasswordField1.getPassword());
            
            if ( Utiles.isNullOrEmpty(pass) ){
                msjLogin.setText("Debe ingresar el pin del token.");
                msjLogin.setVisible(true);
                msjLogin.setForeground(Color.RED);
                return;
            }
            initAliasHashCerts();
            cargarCertificados( pass );
            
        } catch (KeyStoreException ex) {
            Logger.getLogger(PrototipoPAdES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PrototipoPAdES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(PrototipoPAdES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(PrototipoPAdES.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void botonFirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonFirmarActionPerformed
        // TODO add your handling code here:
        
        firmarPDF("46422", "2015");
    }//GEN-LAST:event_botonFirmarActionPerformed

    /*
    public void guardarPdf(String destino, String pdfbase64){
        try {
            byte[] bytes = com.itextpdf.text.pdf.codec.Base64.decode(pdfbase64);
            
            //convert array of bytes into file
            FileOutputStream fileOuputStream = new FileOutputStream( jTextField2.getText() );
            fileOuputStream.write(bytes);
            fileOuputStream.close();
            
            System.out.println("Done");
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PrototipoPAdES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PrototipoPAdES.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    */
    
    public boolean validarPDF( String pdf ){
       return false;
    }
    
    
    /**
     * Firma pdf from javascript.
     * @param pNumero
     * @param pAnio
     * @return 
     */
    public void firmarPDF( String pNumero, String pAnio ){ 
        
        this.solnumero = Integer.valueOf( pNumero );
        this.anio = Integer.valueOf( pAnio );
        if (lista.getSelectedRow() == -1){
            firmarError("Debe seleccionar un certificado.");
            return;
        }
        Thread thread = new Thread(){
            @Override
            public void run(){
                try{
                    seleccionarCertificado();
                    STR str = obtenerSTRFromWS(solnumero, anio, cedula);
                    PAdESFirma infoFirma = generarApariencia();

                    ByteArrayOutputStream pdfOS = firmar(infoFirma, str.getObjetoPdf().getInputStream());
                    InputStream pdfIS = new ByteArrayInputStream( pdfOS.toByteArray() );
                    
                    // Return the InputStream to the client.
                    DataSource ds = new StreamDataSource( pdfIS, pdfOS );
                    DataHandler dh = new DataHandler(ds);
                    str.setObjetoPdf(dh);
                    guardarPDF(str);

                    ocultarMensaje();
                    firmaExitosa("Se ha firmado el documento: " + solnumero);  
                } 
                catch (IOException ex) {
                    Logger.getLogger(PrototipoPAdES.class.getName()).log(Level.SEVERE, null, ex);
                    firmarError( "Error firmando documento: " + solnumero );
                } 
                catch (KeyStoreException ex) {
                    Logger.getLogger(PrototipoPAdES.class.getName()).log(Level.SEVERE, null, ex);
                    firmarError( "Error firmando documento: " + solnumero );
                } 
                catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(PrototipoPAdES.class.getName()).log(Level.SEVERE, null, ex);
                    firmarError( "Error firmando documento: " + solnumero );
                } 
                catch (UnrecoverableKeyException ex) {
                    Logger.getLogger(PrototipoPAdES.class.getName()).log(Level.SEVERE, null, ex);
                    firmarError( "Error firmando documento: " + solnumero );
                }                        
            }
        };
        thread.start();
        mostrarMensaje("Firmando documento: " + solnumero);
  
    }
    
    public PAdESFirma generarApariencia(  ) throws IOException, KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException{
            
        String alias = (String) aliasHash.get(certIndex);
            
        PAdESFirma infofirma = new PAdESFirma();
        infofirma.setPk( (PrivateKey) keystore.getKey(alias, null) );
        infofirma.setChainCert( keystore.getCertificateChain(alias) );
        infofirma.setProvidername( keystore.getProvider().getName() );

        //Definiendo apariencia de firma.
        infofirma.setFirmante(alias);
        infofirma.setTextoFirma(alias);
        infofirma.setApariencia(UtilesResources.getProperty(UtilesResources.PROP_APARIENCIA).equals(UtilesResources.TRUE_VALUE));

        if (infofirma.isApariencia()){
            infofirma.setHoja( Integer.valueOf( UtilesResources.getProperty(UtilesResources.PROP_PAG_FIRMA)) );
            infofirma.setPosicionVertical(UtilesResources.getProperty(UtilesResources.PROP_POS_VERTICAL));
            infofirma.setAncho( Integer.valueOf(UtilesResources.getProperty(UtilesResources.PROP_ANCHO_FIRMA)) );
            infofirma.setLargo( Integer.valueOf(UtilesResources.getProperty(UtilesResources.PROP_LARGO_FIRMA)) );
            infofirma.setRutaImagen( UtilesResources.getProperty(UtilesResources.PROP_URL_IMAGEN) );
        }
        return infofirma;
    }
    
    
    public STR obtenerSTRFromWS( Integer solnumero, Integer anio, String cedula ) throws IOException{
        
        STR str = UtilesWS.getInstancePortWS().obtenerPdfParaFirma(solnumero, anio, cedula);
        
        return str;
    }
    
    public int guardarPDF( STR str ) throws IOException{
        
        int codigoresponse = UtilesWS.getInstancePortWS().guardarPdfFirmado(str, cedula);
        
        return codigoresponse;
    }

    
    
    public ByteArrayOutputStream firmar(PAdESFirma infoFirma, InputStream pdfbase64 ){
        
        try {
            PdfReader reader = new PdfReader( pdfbase64 );
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            PdfStamper stamper = PdfStamper.createSignature(reader, os, '\0', null, true);
            PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
            
            if (infoFirma.isApariencia()){
                System.out.println("Definiendo apariencia...");
                appearance.setSignatureGraphic( Image.getInstance(new URL( infoFirma.getRutaImagen() )) );
                 appearance.setRenderingMode(Utiles.getModoApariencia());           
                
                int numeroPagFirma = infoFirma.getHoja() == -1 ? reader.getNumberOfPages() : infoFirma.getHoja();
                int cantidadFirmaActuales = reader.getAcroFields().getSignatureNames().size();
                int[] coords = infoFirma.calcularCorrdenadasFirma( cantidadFirmaActuales,  infoFirma.getAncho(), infoFirma.getLargo() );
                
                //llx, lly, urx, ury
                appearance.setLayer2Text(infoFirma.getFirmante());
                appearance.setVisibleSignature(new Rectangle(coords[0], coords[1], coords[2], coords[3]), numeroPagFirma, "Id: " + IdGenerator.generate());
            }
            
            ExternalSignature es = new PrivateKeySignature(infoFirma.getPk(), "SHA-256", infoFirma.getProvidername());
            ExternalDigest digest = new BouncyCastleDigest();
            MakeSignature.signDetached(appearance, digest, es, infoFirma.getChainCert(), null, null, null, 0, CryptoStandard.CMS);
           
            System.out.println("PDF Firmado correctamente.");
            
            return os;
            
        } catch (IOException ex) {
            Logger.getLogger(PrototipoPAdES.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (DocumentException ex) {
            Logger.getLogger(PrototipoPAdES.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (KeyStoreException ex) {
            Logger.getLogger(PrototipoPAdES.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(PrototipoPAdES.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (UnrecoverableKeyException ex) {
            Logger.getLogger(PrototipoPAdES.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (GeneralSecurityException ex) {
            Logger.getLogger(PrototipoPAdES.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (Exception ex){
            Logger.getLogger(PrototipoPAdES.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    private void firmarError(String msg){
        JSObject win = (JSObject) JSObject.getWindow(this);
        win.call("firmaError", new String[]{  msg });        
    }
    
    private void firmaExitosa( String msj ){  
        JSObject win = (JSObject) JSObject.getWindow(this);
        win.call("firmaExitosa", new String[]{  msj } );   
    }
    
    /*
    public void test(){
        try {
            
            String alias = (String) aliasHash.get(certIndex);
            PrivateKey pk = (PrivateKey) keystore.getKey(alias, null);;
           
            Certificate[] chain = keystore.getCertificateChain(alias);

            PdfReader reader = new PdfReader(ORIGINAL);
            String idGenerated = IdGenerator.generate();
            String filesigneddoc = SIGNED + idGenerated + ".pdf";
            FileOutputStream os = new FileOutputStream( filesigneddoc );
            PdfStamper stamper = PdfStamper.createSignature(reader, os, '\0');

//          int page = reader.getNumberOfPages();
            //reader.getAcroFields().getSignatureNames().size()
            //llx - lower left x, lly - lower left y, urx - upper right x, ury - upper right y
            
            //PAGINA
            int page = 1;
            
            //POSICION
            float llx = 72;//72;
            float lly = 732;
            float urx = 160;//144;
            float ury = 780;
            
            //COLOR
            int r = 50;
            int g = 160;
            int b = 190;
            
            //***************** SET APARIENCIA DE FIRMA ****************************************
            PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
            
            stamper.insertPage(reader.getNumberOfPages() + 1, reader.getPageSize(1));
//            appearance.setImage(Image.getInstance( RESOURCES ));
            appearance.setReason("Estoy firmando esto por primera vez.");
            appearance.setLocation("Montevideo");
            //Rectángulo de firma - define la apariencia de firma.
            appearance.setVisibleSignature(new Rectangle(llx, lly, urx, ury), reader.getNumberOfPages(), "segunda");
            //appearance.setLayer2Text("Layer 2 text."); texto que aparece en el recuadro.

            Font font = new Font(FontFamily.HELVETICA, 4, Font.BOLDITALIC, new BaseColor(r, g, b));
            appearance.setLayer2Font(font);
            appearance.setSignatureGraphic(Image.getInstance(RESOURCES));
            //*****************CUSTOMIZACION DE LAS FUENTES****************************************
//          appearance.setLayer2Font( new Font(BaseFont.createFont(alias, alias, rootPaneCheckingEnabled), 12) );
            appearance.setRenderingMode(PdfSignatureAppearance.RenderingMode.GRAPHIC_AND_DESCRIPTION);
            
            
            //*****************OBTENER TAMAÑO DE PAGINA *******************************************
            Rectangle mediabox = reader.getPageSize(1);
            System.out.println("Tamaño pagina 1");
            System.out.println("Left: " + mediabox.getLeft());
            System.out.println("Bottom: " + mediabox.getBottom());
            System.out.println("Right: " + mediabox.getRight());
            System.out.println("Top: " + mediabox.getTop());

            // digital signature
            String providername = keystore.getProvider().getName();
            ExternalSignature es = new PrivateKeySignature(pk, "SHA-256", providername);
            ExternalDigest digest = new BouncyCastleDigest();
            MakeSignature.signDetached(appearance, digest, es, chain, null, null, null, 0, CryptoStandard.CMS);
            
            System.out.println("PDF  firmado correctamente.");
//          String destfilecomment = COMMENT + idGenerated + ".pdf";
//          addAnnotation(filesigneddoc, destfilecomment);
            System.out.println("Se ha agregado el comentario correctamente.");
        
        } catch (IOException ex) {
            System.out.println("Error firmando PDF.");
            Logger.getLogger(PrototipoPAdES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            System.out.println("Error firmando PDF.");
            Logger.getLogger(PrototipoPAdES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralSecurityException ex) {
            System.out.println("Error firmando PDF.");
            Logger.getLogger(PrototipoPAdES.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }*/
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonFirmar;
    private javax.swing.JButton jButton5;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable lista;
    private javax.swing.JLabel mensaje;
    private javax.swing.JLabel msjLogin;
    // End of variables declaration//GEN-END:variables
}
