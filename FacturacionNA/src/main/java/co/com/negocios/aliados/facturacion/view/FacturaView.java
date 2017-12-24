package co.com.negocios.aliados.facturacion.view;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import co.com.negocios.aliados.facturacion.dao.FacturaBean;
import co.com.negocios.aliados.facturacion.dto.DetalleFacturaDTO;
import co.com.negocios.aliados.facturacion.dto.EmpresaDTO;
import co.com.negocios.aliados.facturacion.dto.FacturaDTO;
import co.com.negocios.aliados.facturacion.entity.Cliente;
import co.com.negocios.aliados.facturacion.entity.Producto;
import co.com.negocios.aliados.facturacion.utilidades.FacturaPDF;
import co.com.negocios.aliados.facturacion.utilidades.ControlJTextField;
import co.com.negocios.aliados.facturacion.utilidades.UtilidadesFormatos;
import co.com.negocios.aliados.facturacion.utilidades.UtilidadesProperties;
import co.com.negocios.aliados.facturacion.view.reportes.ReportePagosView;
import java.awt.HeadlessException;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

public class FacturaView extends javax.swing.JFrame implements FocusListener {

    private final static Logger LOGGER = Logger.getLogger(FacturaView.class);
 
    private static final int COLUMNA_CERO_CODIGO_PRODUCTO = 0;
    private static final int COLUMNA_UNO_DESCRIPCION = 1;
    private static final int COLUMNA_DOS_INVENTARIO = 2;
    private static final int COLUMNA_TRES_VALOR_UNITARIO = 3;
    private static final int COLUMNA_CUATRO_CANTIDAD = 4;
    private static final int COLUMNA_CINCO_DESCUENTO = 5;
    private static final int COLUMNA_SEIS_SUBTOTAL  = 6;
    private static final int COLUMNA_SIETE_TOTAL = 7;
    /**
     * Creates new form frmPrincipal
     */
    FacturaBean facturaBean = null;
    DefaultTableModel modelo;

    public FacturaView() {
        initComponents();

        facturaBean = new FacturaBean();

       contruirTabla();

        
         habilitarCobro();
        txtDiasPlazo.addKeyListener(new ControlJTextField(ControlJTextField.ENTERO, 8));

        
        lblFechaRemision.setText(UtilidadesFormatos.getFecha(new Date()));
        txtDiasPlazo.addFocusListener(this);
        List<Cliente> consultarClientes = facturaBean.consultarClientes();

        consultarClientes.forEach((consultarCliente) -> {
            cbxCliente.addItem(consultarCliente);
        });
    }

    List<String> listaIdPructo = new ArrayList<>();

       protected void actualizaSumas(TableModelEvent evento) {
        // Solo se trata el evento UPDATE, correspondiente al cambio de valor
        // de una celda.

        if (evento.getType() == TableModelEvent.UPDATE) {
            int fila = evento.getFirstRow();
            int columna = evento.getColumn();
            try {
                String filaIdProducto = ((String) modelo.getValueAt(fila, COLUMNA_CERO_CODIGO_PRODUCTO));
                Producto producto = facturaBean.consultarProducto(filaIdProducto);
                if (producto == null) {
                    JOptionPane.showMessageDialog(null, "El producto que esta intentando agregar no se encuentra registrado", "MENSAJE DEL SISTEMA", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    if (columna == COLUMNA_CERO_CODIGO_PRODUCTO && listaIdPructo.contains(filaIdProducto)) {
                        JOptionPane.showMessageDialog(null, "El producto ya fue agregado (" + filaIdProducto.toUpperCase() + ")", "MENSAJE DEL SISTEMA", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    if (columna == COLUMNA_CERO_CODIGO_PRODUCTO) {
                        modelo.setValueAt(producto.getDescripcion(), fila, COLUMNA_UNO_DESCRIPCION);
                        modelo.setValueAt(producto.getCantidad(), fila, COLUMNA_DOS_INVENTARIO);
                        modelo.setValueAt(producto.getPrecioCompra(), fila, COLUMNA_TRES_VALOR_UNITARIO);
                    }
                    Integer filaCantidad = ((Integer) modelo.getValueAt(fila, COLUMNA_CUATRO_CANTIDAD));
                    if (filaCantidad == null) {
                        filaCantidad = 0;
                    }
                    if (!hayCantidadDisponible(producto.getCantidad(), filaCantidad)) {
                        JOptionPane.showMessageDialog(null, "No hay cantidad suficiente", "MENSAJE DEL SISTEMA", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (columna == COLUMNA_CUATRO_CANTIDAD && filaCantidad <= 0) {
                        JOptionPane.showMessageDialog(null, "Deba digitar una cantidad mayor a 0", "MENSAJE DEL SISTEMA", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    Integer filaValorUnitario = ((Integer) modelo.getValueAt(fila, COLUMNA_TRES_VALOR_UNITARIO));

                    if (filaValorUnitario == null) {
                        filaValorUnitario = 0;
                    }
                    Integer filaDescuento = ((Integer) modelo.getValueAt(fila, COLUMNA_CINCO_DESCUENTO));
                    if (filaDescuento == null) {
                        filaDescuento = 0;
                    }
                    //Agregar dato a columna subtotal y total 
                    if (columna == COLUMNA_CUATRO_CANTIDAD || columna == COLUMNA_TRES_VALOR_UNITARIO || columna == COLUMNA_CINCO_DESCUENTO) {
                        modelo.setValueAt(getTotalSubtotal(filaCantidad, filaValorUnitario), fila, COLUMNA_SEIS_SUBTOTAL );
                        modelo.setValueAt(getTotal(filaCantidad, filaValorUnitario, filaDescuento), fila, COLUMNA_SIETE_TOTAL);
                    }
                    if (!StringUtils.isEmpty(filaIdProducto) && columna == COLUMNA_CUATRO_CANTIDAD) {
                        modelo.addRow(new Object[]{null, null, null, null, null, null, null, null});
                        if (!listaIdPructo.contains(filaIdProducto)) {
                            listaIdPructo.add(filaIdProducto);
                        }
                    }
                    //Actualizar totales                
                    Integer totalDescuento = totales(5);
                    Integer subtotal = totales(6);
                    Integer total = totales(7);

                    lblDescuento.setText(UtilidadesFormatos.formatoMonto(totalDescuento));
                    lblSubTotal.setText(UtilidadesFormatos.formatoMonto(subtotal));
                    lblTotal.setText(UtilidadesFormatos.formatoMonto(total));
                }

            } catch (HeadlessException | NumberFormatException e) {
                LOGGER.error(ExceptionUtils.getFullStackTrace(e));
                JOptionPane.showMessageDialog(null, "Ocurrió un error inesperado, comuniquese con el adminstrador del sistema", "MENSAJE DEL SISTEMA", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean hayCantidadDisponible(Long codigoProducto, Integer cantidadTabla) {
        return cantidadTabla <= codigoProducto;
    }

    private FacturaDTO getFactura() {
        FacturaDTO facturaDTO = new FacturaDTO();
        Cliente name = (Cliente) getCbxCliente().getSelectedItem();
        facturaDTO.setCodigoCliente(name.getNitDocumento());
        facturaDTO.setCodFormapago(1L);
        facturaDTO.setFechaFacturacion(new Date());

        facturaDTO.setObservacion(txtObservacion.getText());
        facturaDTO.setNombreEmpleado("Diana Ruiz");
        facturaDTO.setTotalDescuento(new Long(totales(COLUMNA_CINCO_DESCUENTO)));
        facturaDTO.setSubtotal(new Long(totales(COLUMNA_SEIS_SUBTOTAL )));
        facturaDTO.setTotalFactura(new Long(totales(COLUMNA_SIETE_TOTAL)));
        return facturaDTO;
    }

    private List<DetalleFacturaDTO> getDetalleFactura() throws Exception {
        List<DetalleFacturaDTO> listaDetalleFactura = new ArrayList<>();
        List<String> listaIdPructo = new ArrayList<>();

        DetalleFacturaDTO detalleFacturaDTO = null;
        for (int i = 0; i < modelo.getRowCount(); i++) {
            detalleFacturaDTO = new DetalleFacturaDTO();

            String codigoProducto = ((String) modelo.getValueAt(i, COLUMNA_CERO_CODIGO_PRODUCTO));

            if (!StringUtils.isEmpty(codigoProducto)) {
                Integer cantidad = ((Integer) modelo.getValueAt(i, COLUMNA_CUATRO_CANTIDAD));
                Integer valorUnitario = ((Integer) modelo.getValueAt(i, COLUMNA_TRES_VALOR_UNITARIO));
                Integer descuento = ((Integer) modelo.getValueAt(i, COLUMNA_CINCO_DESCUENTO));
                Integer subtotal = ((Integer) modelo.getValueAt(i, COLUMNA_SEIS_SUBTOTAL ));
                Integer total = ((Integer) modelo.getValueAt(i, COLUMNA_SIETE_TOTAL));

                detalleFacturaDTO.setCodigoProducto(codigoProducto);
                detalleFacturaDTO.setCantidad(cantidad == null ? 0 : cantidad);
                detalleFacturaDTO.setCosto(new BigDecimal(valorUnitario));
                detalleFacturaDTO.setDescuento(descuento == null ? new BigDecimal(0) : new BigDecimal(descuento));
                detalleFacturaDTO.setSubtotal(subtotal);
                detalleFacturaDTO.setTotal(total);

                listaDetalleFactura.add(detalleFacturaDTO);
            }
        }
        return listaDetalleFactura;
    }

    private Integer getTotal(Integer filaCantidad, Integer filaValorUnitario, Integer filaDescuento) {
        return ((filaCantidad * filaValorUnitario) - filaDescuento);
    }

    private Integer totales(int columna) {
        int totalRow = jTable1.getRowCount();
        totalRow -= 1;
        Integer totalLocal = 0;
        for (int i = 0; i <= (totalRow); i++) {
            Integer columnaValorTotal = ((Integer) modelo.getValueAt(i, columna));
            if (columnaValorTotal != null) {
                totalLocal = totalLocal + columnaValorTotal;
            }
        }
        return totalLocal;
    }

    private void limpiarTablaYCampos() {
        cbxCliente.setSelectedItem("");
        lblNit.setText("Nit");
        lblDireccion.setText("Dirección");
        lblTelfono.setText("Teléfono");

        lblDescuento.setText("$0");
        lblSubTotal.setText("$0");
        lblTotal.setText("$0");

        for (int i = 0; i < jTable1.getRowCount(); i++) {
            modelo.removeRow(i);
            i -= 1;
        }
        modelo.addRow(new Object[]{null, null, null, null, null, null, null, null});
        listaIdPructo.clear();
    }

    private boolean validarDatosObligatorios() {

        if (StringUtils.isEmpty(getCbxCliente().getSelectedItem().toString().trim())) {
            JOptionPane.showMessageDialog(null, "Debe selecconar un cliente", "MENSAJE DEL SISTEMA", JOptionPane.ERROR_MESSAGE);
            cbxCliente.setSelectedItem("");
            return false;
        }
        Cliente cliente = (Cliente) getCbxCliente().getSelectedItem();
        if (cliente == null) {
            JOptionPane.showMessageDialog(null, "Debe selecconar un cliente", "MENSAJE DEL SISTEMA", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String filaIdProducto = ((String) modelo.getValueAt(0, 0));
        if (modelo.getRowCount() == 0 || (modelo.getRowCount() == 1 && StringUtils.isEmpty(filaIdProducto))) {
            JOptionPane.showMessageDialog(null, "Debe agregar almenos un producto", "MENSAJE DEL SISTEMA", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (StringUtils.isEmpty(txtDiasPlazo.getText())) {
            JOptionPane.showMessageDialog(null, "Debe digitar los días de plazo", "MENSAJE DEL SISTEMA", JOptionPane.ERROR_MESSAGE);
//            return false;
        }
        return true;
    }

    private void anadeListenerAlModelo(JTable tabla) {
        tabla.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent evento) {
                actualizaSumas(evento);
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupPago = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblFechaVencimiento = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        cbxCliente = new javax.swing.JComboBox();
        lblNit = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblTelfono = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        txtDiasPlazo = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        lblFechaVencimiento1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtValor = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        lblFechaRemision = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtObservacion = new javax.swing.JTextArea();
        jLabel23 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblTotal = new javax.swing.JLabel();
        lblDescuento = new javax.swing.JLabel();
        lblDescuentoDes = new javax.swing.JLabel();
        lblTotalDes = new javax.swing.JLabel();
        lblSubtotalDes = new javax.swing.JLabel();
        lblSubTotal = new javax.swing.JLabel();
        btnGenerarFactura = new javax.swing.JButton();
        btnEliminarProducto = new javax.swing.JButton();
        btnNuevaFactura = new javax.swing.JButton();
        btnGenerarFacturaExistente = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem19 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(150, 10));

        jLabel1.setFont(new java.awt.Font("Sylfaen", 1, 14)); // NOI18N
        jLabel1.setText("GESTIÓN DE FACTURA");

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0))));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Productos"));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Datos del cliente"));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Cliente:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Nit:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Dirección:");

        lblFechaVencimiento.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblFechaVencimiento.setText("010101");

        lblDireccion.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblDireccion.setText("dirección");

        cbxCliente.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxCliente.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " " }));
        cbxCliente.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxClienteItemStateChanged(evt);
            }
        });

        lblNit.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblNit.setText("Nit");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Teléfono:");

        lblTelfono.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblTelfono.setText("Téfono");

        buttonGroupPago.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("Pagado");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        buttonGroupPago.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jRadioButton2.setText("Por cobrar");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        txtDiasPlazo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtDiasPlazo.setText("0");
        txtDiasPlazo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDiasPlazoActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Días de plazo:");

        lblFechaVencimiento1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblFechaVencimiento1.setText("Facha vencimieto:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Valor:");

        txtValor.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtValor.setText("0");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTelfono, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNit, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(txtDiasPlazo, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(55, 55, 55)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblFechaVencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jRadioButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton2)
                        .addGap(72, 72, 72)
                        .addComponent(lblFechaVencimiento1)))
                .addContainerGap(85, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbxCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNit)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblDireccion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lblTelfono))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(lblFechaVencimiento1)
                    .addComponent(lblFechaVencimiento))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtDiasPlazo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Fecha Remisión:");

        lblFechaRemision.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblFechaRemision.setText("010101");
        lblFechaRemision.setToolTipText("");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Vendedor:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("Diana Ruiz");

        txtObservacion.setColumns(20);
        txtObservacion.setRows(5);
        jScrollPane2.setViewportView(txtObservacion);

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel23.setText("Observaciones:");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblTotal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblTotal.setText("$0");

        lblDescuento.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblDescuento.setText("$0");

        lblDescuentoDes.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblDescuentoDes.setText("DESCUENTO:");

        lblTotalDes.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblTotalDes.setText("TOTAL:");

        lblSubtotalDes.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblSubtotalDes.setText("SUBTOTAL:");

        lblSubTotal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblSubTotal.setText("$0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblTotalDes)
                        .addGap(50, 50, 50)
                        .addComponent(lblTotal))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblSubtotalDes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDescuentoDes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDescuento)
                            .addComponent(lblSubTotal))))
                .addContainerGap(110, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDescuentoDes)
                    .addComponent(lblDescuento))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSubtotalDes)
                    .addComponent(lblSubTotal))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotal)
                    .addComponent(lblTotalDes))
                .addContainerGap())
        );

        btnGenerarFactura.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnGenerarFactura.setText("Generar factura");
        btnGenerarFactura.setActionCommand("");
        btnGenerarFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarFacturaActionPerformed(evt);
            }
        });

        btnEliminarProducto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnEliminarProducto.setText("Elimiar Producto");
        btnEliminarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProductoActionPerformed(evt);
            }
        });

        btnNuevaFactura.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnNuevaFactura.setText("Nueva factura");
        btnNuevaFactura.setActionCommand("");
        btnNuevaFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaFacturaActionPerformed(evt);
            }
        });

        btnGenerarFacturaExistente.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnGenerarFacturaExistente.setText("Generar factura existente");
        btnGenerarFacturaExistente.setActionCommand("");
        btnGenerarFacturaExistente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarFacturaExistenteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(btnEliminarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabel23)
                .addGap(35, 35, 35)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(btnGenerarFactura)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGenerarFacturaExistente)
                .addGap(51, 51, 51))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(18, 18, Short.MAX_VALUE)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(43, 43, 43)
                                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(lblFechaRemision, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)))
                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(36, 36, 36))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(106, 106, 106)
                                .addComponent(btnNuevaFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(btnNuevaFactura)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(lblFechaRemision, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEliminarProducto)
                            .addComponent(jLabel23)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnGenerarFactura)
                            .addComponent(btnGenerarFacturaExistente))))
                .addGap(34, 34, 34))
        );

        jMenu1.setText("Movimientos");

        jMenuItem1.setText("Ventas");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem3.setText("Compras");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Administración");

        jMenuItem6.setText("Clientes");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem6);

        jMenuItem5.setText("Productos");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuItem2.setText("Tipo producto");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuItem7.setText("Proveedores");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem7);

        jMenuBar1.add(jMenu2);

        jMenu4.setText("Reportes");

        jMenuItem8.setText("Remisiones por cobrar");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem8);

        jMenuBar1.add(jMenu4);

        jMenu5.setText("Ayuda");

        jMenuItem19.setText("Información de la empresa");
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem19);

        jMenuBar1.add(jMenu5);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(392, 392, 392))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleName("Busqueda");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        ComprasView comprasView = new ComprasView(this, true);
        comprasView.setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        ClienteView clienteView = new ClienteView(this, true);
        clienteView.setVisible(true);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        ProductoView productoView2 = new ProductoView(this, true);
        productoView2.setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
        ProveedoresView proveedorView = new ProveedoresView(this, true);
        proveedorView.setVisible(true);
    }//GEN-LAST:event_jMenuItem7ActionPerformed


    private void btnGenerarFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarFacturaActionPerformed
        LOGGER.info("Inicio generar factura");
        try {
            if (validarDatosObligatorios()) {

                FacturaDTO facturaDTO = getFactura();
                facturaDTO.setDetalleFacturaList(getDetalleFactura());
                Cliente cliente = (Cliente) cbxCliente.getSelectedItem();

                Long secuenciaFacura = facturaBean.guardarFactura(facturaDTO, cliente);

                facturaDTO.setNumeroFactura(secuenciaFacura);

                JOptionPane.showMessageDialog(null, "Registro exitoso", "MENSAJE DEL SISTEMA", JOptionPane.INFORMATION_MESSAGE);
                generarFactura(facturaDTO, cliente);

                limpiarTablaYCampos();
            }

        } catch (Exception e) {
            LOGGER.error(ExceptionUtils.getFullStackTrace(e));
            JOptionPane.showMessageDialog(null, "Ocurrió un error inesperado, comuniquese con el adminstrador del sistema", "MENSAJE DEL SISTEMA", JOptionPane.ERROR_MESSAGE);
        }
        LOGGER.info("Fin generar factura");
    }//GEN-LAST:event_btnGenerarFacturaActionPerformed

    private void generarFactura(FacturaDTO facturaDTO, Cliente cliente) {

        FacturaPDF facturaPDF = new FacturaPDF();

        UtilidadesProperties properties = new UtilidadesProperties();
        EmpresaDTO consultarEmpresa = properties.consultarDatosEmpresa();

        if (consultarEmpresa == null) {
            JOptionPane.showMessageDialog(null, "No se encontró empresa asociada al sistema", "MENSAJE DEL SISTEMA", JOptionPane.ERROR_MESSAGE);
        }
        facturaPDF.generarFactura(facturaDTO, consultarEmpresa, cliente);
    }

    private void btnEliminarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProductoActionPerformed
        // TODO add your handling code here:
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel(); //TableProducto es el nombre de mi tabla ;) 
        String filaIdProducto = ((String) modelo.getValueAt(jTable1.getSelectedRow(), COLUMNA_CERO_CODIGO_PRODUCTO));
        modelo.removeRow(jTable1.getSelectedRow());
        listaIdPructo.remove(filaIdProducto);

    }//GEN-LAST:event_btnEliminarProductoActionPerformed

    private void btnNuevaFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaFacturaActionPerformed
        // TODO add your handling code here:
        int boton = JOptionPane.showConfirmDialog(this, "Se perderá toda la información digitada hasta el momento \n ¿está seguro que desea inicar de nuevo?", "Nueva Remisión", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

        if (boton == JOptionPane.YES_OPTION) {
            limpiarTablaYCampos();
        }
    }//GEN-LAST:event_btnNuevaFacturaActionPerformed

    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed
        // TODO add your handling code here:
        EmpresaView empresaView = new EmpresaView(this, true);
        empresaView.setVisible(true);
    }//GEN-LAST:event_jMenuItem19ActionPerformed

    private void btnGenerarFacturaExistenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarFacturaExistenteActionPerformed
        // TODO add your handling code here:
        LOGGER.info("Inicio generar factura existente");

        Long idFactura = 0L;
        try {
            idFactura = new Long(JOptionPane.showInputDialog("Ingrese número de la facura"));
        } catch (NumberFormatException e) {
            LOGGER.error(ExceptionUtils.getFullStackTrace(e));
            JOptionPane.showMessageDialog(null, "Debe ingresar un valor numérico", "MENSAJE DEL SISTEMA", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            FacturaDTO facturaDTO = facturaBean.consultarFacturaDto(idFactura);
            Cliente cliente = facturaBean.consultarCliente(facturaDTO.getCodigoCliente());
            generarFactura(facturaDTO, cliente);
        } catch (Exception e) {
            LOGGER.error(ExceptionUtils.getFullStackTrace(e));
            JOptionPane.showMessageDialog(null, "Ocurrió un error inesperado, comuniquese con el adminstrador del sistema", "MENSAJE DEL SISTEMA", JOptionPane.ERROR_MESSAGE);
        }
        LOGGER.info("Fin generar factura existente");
    }//GEN-LAST:event_btnGenerarFacturaExistenteActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        TipoArticuloView articuloView = new TipoArticuloView(this, true);
        articuloView.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void cbxClienteItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxClienteItemStateChanged
        // TODO add your handling code here:

        Cliente cliente = (Cliente) cbxCliente.getSelectedItem();
        if (cliente != null) {
            lblNit.setText(cliente.getNitDocumento());
            lblDireccion.setText(cliente.getDireccion());
            lblTelfono.setText(cliente.getTelefono());
        }
    }//GEN-LAST:event_cbxClienteItemStateChanged

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // TODO add your handling code here:
        inhabilitarCobro();
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
        ReportePagosView pagosView = new ReportePagosView(this, true);
        pagosView.setVisible(true);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void txtDiasPlazoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiasPlazoActionPerformed
        // TODO add your handling code here:
        int dias = Integer.parseInt(txtDiasPlazo.getText());
        lblFechaVencimiento.setText(UtilidadesFormatos.getFecha(UtilidadesFormatos.sumarRestarDiasFecha(new Date(), dias)));
    }//GEN-LAST:event_txtDiasPlazoActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
        habilitarCobro();
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void habilitarCobro() {
        txtDiasPlazo.setEditable(false);
        txtValor.setEditable(false);
    }

    private void inhabilitarCobro() {
        txtDiasPlazo.setEditable(true);
        txtValor.setEditable(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        LOGGER.info("Inicio sitema");
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FacturaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FacturaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FacturaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FacturaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }//        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FacturaView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminarProducto;
    private javax.swing.JButton btnGenerarFactura;
    private javax.swing.JButton btnGenerarFacturaExistente;
    private javax.swing.JButton btnNuevaFactura;
    private javax.swing.ButtonGroup buttonGroupPago;
    private javax.swing.JComboBox cbxCliente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblDescuento;
    private javax.swing.JLabel lblDescuentoDes;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblFechaRemision;
    private javax.swing.JLabel lblFechaVencimiento;
    private javax.swing.JLabel lblFechaVencimiento1;
    private javax.swing.JLabel lblNit;
    private javax.swing.JLabel lblSubTotal;
    private javax.swing.JLabel lblSubtotalDes;
    private javax.swing.JLabel lblTelfono;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblTotalDes;
    private javax.swing.JTextField txtDiasPlazo;
    private javax.swing.JTextArea txtObservacion;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables

    public JComboBox getCbxCliente() {
        return cbxCliente;
    }

    public void setCbxCliente(JComboBox cbxCliente) {
        this.cbxCliente = cbxCliente;
    }

    private Integer getTotalSubtotal(Integer filaCantidad, Integer filaValorUnitario) {
        return filaCantidad * filaValorUnitario;
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource() == txtDiasPlazo) {
            int dias = Integer.parseInt(txtDiasPlazo.getText());
            lblFechaVencimiento.setText(UtilidadesFormatos.getFecha(UtilidadesFormatos.sumarRestarDiasFecha(new Date(), dias)));
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() == txtDiasPlazo) {
            int dias = Integer.parseInt(txtDiasPlazo.getText());
            lblFechaVencimiento.setText(UtilidadesFormatos.getFecha(UtilidadesFormatos.sumarRestarDiasFecha(new Date(), dias)));
        }
    }

    private void contruirTabla() {
        
        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 || column == 3 || column == 4 || column == 5;
            }

            @Override
            public Class<?> getColumnClass(int columna) {
                switch (columna) {
                    case COLUMNA_DOS_INVENTARIO:
                        return Integer.class;
                    case COLUMNA_TRES_VALOR_UNITARIO:
                        return Integer.class;
                    case COLUMNA_CUATRO_CANTIDAD:
                        return Integer.class;
                    case COLUMNA_CINCO_DESCUENTO:
                        return Integer.class;
                    case COLUMNA_SEIS_SUBTOTAL :
                        return Integer.class;
                    case COLUMNA_SIETE_TOTAL:
                        return Integer.class;
                    default:
                        return String.class;
                }

            }
        };
       
        modelo.addColumn("Referencia ");
        modelo.isCellEditable(0, COLUMNA_CERO_CODIGO_PRODUCTO);
        modelo.addColumn("Descripción");
        modelo.addColumn("Inventario");
        modelo.getColumnClass(COLUMNA_DOS_INVENTARIO);
        modelo.addColumn("Valor unitario");
        modelo.getColumnClass(COLUMNA_TRES_VALOR_UNITARIO);
        modelo.isCellEditable(0, COLUMNA_TRES_VALOR_UNITARIO);
        modelo.addColumn("Cantidad");
        modelo.getColumnClass(COLUMNA_CUATRO_CANTIDAD);
        modelo.isCellEditable(0, COLUMNA_CUATRO_CANTIDAD);
        modelo.addColumn("Descuento");
        modelo.getColumnClass(COLUMNA_CINCO_DESCUENTO);
        modelo.isCellEditable(0, COLUMNA_CINCO_DESCUENTO);
        modelo.addColumn("Subtotal");
        modelo.getColumnClass(COLUMNA_SEIS_SUBTOTAL );
        modelo.addColumn("Total");
        modelo.getColumnClass(COLUMNA_SIETE_TOTAL);

        jTable1.setModel(modelo);
        modelo.addRow(new Object[]{null, null, null, null, null, null, null, null});
        anadeListenerAlModelo(jTable1);
    }
}
