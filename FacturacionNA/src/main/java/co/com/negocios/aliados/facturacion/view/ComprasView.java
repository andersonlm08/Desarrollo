/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.negocios.aliados.facturacion.view;

import co.com.negocios.aliados.facturacion.dao.CompraBean;
import co.com.negocios.aliados.facturacion.dto.CompraDTO;
import co.com.negocios.aliados.facturacion.dto.DetalleCompraDTO;
import co.com.negocios.aliados.facturacion.entity.Producto;
import co.com.negocios.aliados.facturacion.entity.Proveedor;
import co.com.negocios.aliados.facturacion.utilidades.ControlJTextField;
import co.com.negocios.aliados.facturacion.utilidades.UtilidadesFormatos;
import java.awt.HeadlessException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author jorge.lopez
 */
public class ComprasView extends java.awt.Dialog {

    final static Logger LOGGER = Logger.getLogger(ComprasView.class);

    /**
     * Creates new form ComprasView
     */
    CompraBean compraBean = null;

    DefaultTableModel modelo;

    List<String> listaIdPructo = new ArrayList<>();

    private static final int COLUMNA_CERO_CODIGO_PRODUCTO = 0;
    private static final int COLUMNA_UNO_DESCRIPCION = 1;
    private static final int COLUMNA_DOS_INVENTARIO = 2;
    private static final int COLUMNA_TRES_VALOR_UNITARIO = 3;
    private static final int COLUMNA_CUATRO_CANTIDAD = 4;
    private static final int COLUMNA_CINCO_DESCUENTO = 5;
    private static final int COLUMNA_SEIS_SUBTOTAL = 6;
    private static final int COLUMNA_SIETE_TOTAL = 7;

    public ComprasView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        compraBean = new CompraBean();

        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 || column == 3 || column == 4 || column == 5;
            }

            @Override
            public Class<?> getColumnClass(int columna) {
                if (columna == COLUMNA_DOS_INVENTARIO) {
                    return Integer.class;
                } else if (columna == COLUMNA_TRES_VALOR_UNITARIO) {
                    return Integer.class;
                } else if (columna == COLUMNA_CUATRO_CANTIDAD) {
                    return Integer.class;
                } else if (columna == COLUMNA_CINCO_DESCUENTO) {
                    return Integer.class;
                } else if (columna == COLUMNA_SEIS_SUBTOTAL) {
                    return Integer.class;
                } else if (columna == COLUMNA_SIETE_TOTAL) {
                    return Integer.class;
                } else {
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
        modelo.getColumnClass(COLUMNA_SEIS_SUBTOTAL);
        modelo.addColumn("Total");
        modelo.getColumnClass(COLUMNA_SIETE_TOTAL);

        jTable1.setModel(modelo);
        modelo.addRow(new Object[]{null, null, null, null, null, null, null, null});
        anadeListenerAlModelo(jTable1);

        List<Proveedor> listaProveedores = compraBean.consultarProveedores();
        for (Proveedor proveedor : listaProveedores) {
            cbxProveedores.addItem(proveedor);
        }

        txtDiasPlazo.addKeyListener(new ControlJTextField(ControlJTextField.ENTERO, 8));

        lblFechaVencimiento.setText(UtilidadesFormatos.getFecha(new Date()));
        lblFechaRegistro.setText(UtilidadesFormatos.getFecha(new Date()));
    }

    private void anadeListenerAlModelo(JTable tabla) {
        tabla.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent evento) {
                actualizaSumas(evento);
            }
        });
    }

    private boolean validarDatosObligatorios() {

        if (StringUtils.isEmpty(cbxProveedores.getSelectedItem().toString().trim())) {
            JOptionPane.showMessageDialog(null, "Debe selecconar un proveedor", "MENSAJE DEL SISTEMA", JOptionPane.ERROR_MESSAGE);
            cbxProveedores.setSelectedItem("");
            return false;
        }

        Proveedor proveedor = (Proveedor) cbxProveedores.getSelectedItem();

        if (proveedor == null) {
            JOptionPane.showMessageDialog(null, "Debe selecconar un proveedor", "MENSAJE DEL SISTEMA", JOptionPane.ERROR_MESSAGE);
            cbxProveedores.setSelectedItem("");
            return false;
        }

        Calendar calendar = calFechaCompra.getCalendar();

        if (calendar == null) {
            JOptionPane.showMessageDialog(null, "Debe selecconar fecha de compra", "MENSAJE DEL SISTEMA", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (StringUtils.isEmpty(txtFacturaCompra.getText())) {
            JOptionPane.showMessageDialog(null, "Debe digitar un numero de factura", "MENSAJE DEL SISTEMA", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (StringUtils.isEmpty(txtDiasPlazo.getText())) {
            JOptionPane.showMessageDialog(null, "Debe digitar los días de plazo", "MENSAJE DEL SISTEMA", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        String filaIdProducto = ((String) modelo.getValueAt(0, 0));
        if (modelo.getRowCount() == 0 || (modelo.getRowCount() == 1 && StringUtils.isEmpty(filaIdProducto))) {
            JOptionPane.showMessageDialog(null, "Debe agregar almenos un producto", "MENSAJE DEL SISTEMA", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private List<DetalleCompraDTO> getDetalleCompra() throws Exception {
        List<DetalleCompraDTO> listaDetalleFactura = new ArrayList<>();
        DetalleCompraDTO detalleCompraDTO = null;
        for (int i = 0; i < modelo.getRowCount(); i++) {
            detalleCompraDTO = new DetalleCompraDTO();

            String codigoProducto = ((String) modelo.getValueAt(i, COLUMNA_CERO_CODIGO_PRODUCTO));

            if (!StringUtils.isEmpty(codigoProducto)) {
                Integer cantidad = ((Integer) modelo.getValueAt(i, COLUMNA_CUATRO_CANTIDAD));
                Integer valorUnitario = ((Integer) modelo.getValueAt(i, COLUMNA_TRES_VALOR_UNITARIO));
                Integer descuento = ((Integer) modelo.getValueAt(i, COLUMNA_CINCO_DESCUENTO));
                Integer subtotal = ((Integer) modelo.getValueAt(i, COLUMNA_SEIS_SUBTOTAL));
                Integer total = ((Integer) modelo.getValueAt(i, COLUMNA_SIETE_TOTAL));

                detalleCompraDTO.setCodigoProducto(codigoProducto);
                detalleCompraDTO.setCantidad(cantidad == null ? 0 : cantidad);
                detalleCompraDTO.setCosto(new BigDecimal(valorUnitario));
                detalleCompraDTO.setDescuento(descuento == null ? new BigDecimal(0) : new BigDecimal(descuento));
                detalleCompraDTO.setSubtotal(subtotal);
                detalleCompraDTO.setTotal(total);

                listaDetalleFactura.add(detalleCompraDTO);
            }
        }
        return listaDetalleFactura;
    }

    protected void actualizaSumas(TableModelEvent evento) {
        // Solo se trata el evento UPDATE, correspondiente al cambio de valor
        // de una celda.

        if (evento.getType() == TableModelEvent.UPDATE) {
            int fila = evento.getFirstRow();
            int columna = evento.getColumn();
            try {
                String filaIdProducto = ((String) modelo.getValueAt(fila, COLUMNA_CERO_CODIGO_PRODUCTO));
                Producto producto = compraBean.consultarProducto(filaIdProducto);
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
                        modelo.setValueAt(getTotalSubtotal(filaCantidad, filaValorUnitario), fila, COLUMNA_SEIS_SUBTOTAL);
                        modelo.setValueAt(getTotal(filaCantidad, filaValorUnitario, filaDescuento), fila, COLUMNA_SIETE_TOTAL);
                    }
                    if (!StringUtils.isEmpty(filaIdProducto) && columna == COLUMNA_CUATRO_CANTIDAD) {
                        modelo.addRow(new Object[]{null, null, null, null, null, null, null, null});
                        listaIdPructo.add(filaIdProducto);
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

    private Integer getTotal(Integer filaCantidad, Integer filaValorUnitario, Integer filaDescuento) {
        return ((filaCantidad * filaValorUnitario) - filaDescuento);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtDiasPlazo = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cbxProveedores = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        calFechaCompra = new com.toedter.calendar.JDateChooser();
        txtFacturaCompra = new javax.swing.JTextField();
        lblTelefono = new javax.swing.JLabel();
        lblFechaVencimiento = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblFechaRegistro = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtObservacion = new javax.swing.JTextArea();
        jLabel23 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblTotal = new javax.swing.JLabel();
        lblDescuento = new javax.swing.JLabel();
        lblDescuentoDes = new javax.swing.JLabel();
        lblTotalDes = new javax.swing.JLabel();
        lblSubTotal = new javax.swing.JLabel();
        lblSubtotalDes = new javax.swing.JLabel();
        btnGenerarCompra = new javax.swing.JButton();
        btnEliminarProducto = new javax.swing.JButton();
        btnNuevaFactura = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setLocation(new java.awt.Point(300, 50));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Datos de la compra"));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Proveedor:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("No. Factura de compra:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Fecha compra:");

        txtDiasPlazo.setText("0");
        txtDiasPlazo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDiasPlazoActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Días de plazo:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Facha vencimieto:");

        cbxProveedores.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxProveedores.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " " }));
        cbxProveedores.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxProveedoresItemStateChanged(evt);
            }
        });

        jLabel9.setText("Teléfono:");

        lblTelefono.setText("Teléfono");

        lblFechaVencimiento.setText("fecha vencimiento");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel2)
                    .addComponent(jLabel9)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(96, 96, 96)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(calFechaCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txtFacturaCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblFechaVencimiento, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                            .addComponent(txtDiasPlazo, javax.swing.GroupLayout.Alignment.LEADING))))
                .addContainerGap(74, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbxProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(calFechaCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtFacturaCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtDiasPlazo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFechaVencimiento)
                    .addComponent(jLabel5))
                .addContainerGap())
        );

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Fecha Registro:");

        lblFechaRegistro.setText("010101");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Adminstrador:");

        jLabel12.setText("Diana Ruiz");

        txtObservacion.setColumns(20);
        txtObservacion.setRows(5);
        jScrollPane2.setViewportView(txtObservacion);

        jLabel23.setText("Observaciones:");

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblTotal.setText("$0");

        lblDescuento.setText("$0");

        lblDescuentoDes.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblDescuentoDes.setText("DESCUENTO:");

        lblTotalDes.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblTotalDes.setText("TOTAL:");

        lblSubTotal.setText("$0");

        lblSubtotalDes.setText("SUBTOTAL:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(lblTotalDes)
                                .addGap(50, 50, 50)
                                .addComponent(lblTotal))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(lblDescuentoDes, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblSubTotal)
                                    .addComponent(lblDescuento))))
                        .addGap(0, 83, Short.MAX_VALUE))
                    .addComponent(lblSubtotalDes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDescuentoDes)
                    .addComponent(lblDescuento))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSubtotalDes)
                    .addComponent(lblSubTotal))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotal)
                    .addComponent(lblTotalDes))
                .addContainerGap())
        );

        btnGenerarCompra.setText("Guardar compra");
        btnGenerarCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarCompraActionPerformed(evt);
            }
        });

        btnEliminarProducto.setText("Elimiar Producto");
        btnEliminarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProductoActionPerformed(evt);
            }
        });

        btnNuevaFactura.setText("Nueva compra");
        btnNuevaFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaFacturaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(btnEliminarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel23)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGenerarCompra)
                .addGap(142, 142, 142))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(41, 41, 41)
                                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(lblFechaRegistro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)))
                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(36, 36, 36))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnNuevaFactura)
                                .addGap(91, 91, 91))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(36, 36, 36))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(btnNuevaFactura)
                        .addGap(28, 28, 28)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(lblFechaRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(btnGenerarCompra)
                                .addContainerGap(55, Short.MAX_VALUE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnEliminarProducto)
                                    .addComponent(jLabel23))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jLabel1.setFont(new java.awt.Font("Sylfaen", 1, 14)); // NOI18N
        jLabel1.setText("GESTION DE COMPRAS");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(356, 356, 356)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(44, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    public Date sumarRestarDiasFecha(Date fecha, int dias) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); // Configuramos la fecha que se recibe	
        calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días<0	
        return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos	
    }

    private void limpiarTablaYCampos() {

        cbxProveedores.setSelectedItem("");
        lblTelefono.setText("Teléfono");
        calFechaCompra.setDate(null);

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

    private CompraDTO getCompra() {

        CompraDTO compraDTO = new CompraDTO();

        compraDTO.setFacturaCompra(txtFacturaCompra.getText());
        compraDTO.setFechaRegistro(new Date());
        compraDTO.setFechaCompra(calFechaCompra.getDate());
        compraDTO.setDiasPlazo(Long.parseLong(txtDiasPlazo.getText()));
        compraDTO.setFechaVencimiento(UtilidadesFormatos.toDate(lblFechaVencimiento.getText()));
        compraDTO.setUsuarioRegistro("Diana Ruiz");
        compraDTO.setTotalCompra(new Long(totales(COLUMNA_SIETE_TOTAL)));
        compraDTO.setIva(0L);

        compraDTO.setValorPagado(new BigDecimal(0));
        compraDTO.setDebe(new BigDecimal(0));

        compraDTO.setObservacion(txtObservacion.getText());
        Proveedor name = (Proveedor) cbxProveedores.getSelectedItem();
        compraDTO.setNitProveedor(name.getNitDocumento());
        return compraDTO;
    }

    private Integer getTotalSubtotal(Integer filaCantidad, Integer filaValorUnitario) {
        return filaCantidad * filaValorUnitario;
    }

    private void btnGenerarCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarCompraActionPerformed
        // TODO add your handling code here:

        LOGGER.info("Inicio generar compra");
        try {
            if (validarDatosObligatorios()) {

                CompraDTO compraDTO = getCompra();
                List<DetalleCompraDTO> listaDetalle = getDetalleCompra();
                compraDTO.setDetalleCompraList(listaDetalle);
                Proveedor proveedor = (Proveedor) cbxProveedores.getSelectedItem();
                compraBean.guardarCompra(compraDTO, proveedor);
                JOptionPane.showMessageDialog(null, "Registro exitoso", "MENSAJE DEL SISTEMA", JOptionPane.INFORMATION_MESSAGE);
                limpiarTablaYCampos();
            }

        } catch (Exception e) {
            LOGGER.error(ExceptionUtils.getFullStackTrace(e));
            JOptionPane.showMessageDialog(null, "Ocurrió un error inesperado, comuniquese con el adminstrador del sistema", "MENSAJE DEL SISTEMA", JOptionPane.ERROR_MESSAGE);
        }
        LOGGER.info("Fin generar compra");

    }//GEN-LAST:event_btnGenerarCompraActionPerformed


    private void btnEliminarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProductoActionPerformed
        // TODO add your handling code here:
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel(); //TableProducto es el nombre de mi tabla ;) 
        String filaIdProducto = ((String) modelo.getValueAt(jTable1.getSelectedRow(), COLUMNA_CERO_CODIGO_PRODUCTO));
        modelo.removeRow(jTable1.getSelectedRow());
        listaIdPructo.remove(filaIdProducto);
    }//GEN-LAST:event_btnEliminarProductoActionPerformed

    private void cbxProveedoresItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxProveedoresItemStateChanged
        // TODO add your handling code here:
        Proveedor proveedor = (Proveedor) cbxProveedores.getSelectedItem();
        if (proveedor != null) {
            lblTelefono.setText(proveedor.getTelefono());
        }

    }//GEN-LAST:event_cbxProveedoresItemStateChanged

    private void txtDiasPlazoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiasPlazoActionPerformed
        // TODO add your handling code here:
        int dias = Integer.parseInt(txtDiasPlazo.getText());
        lblFechaVencimiento.setText(UtilidadesFormatos.getFecha(sumarRestarDiasFecha(new Date(), dias)));
    }//GEN-LAST:event_txtDiasPlazoActionPerformed

    private void btnNuevaFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaFacturaActionPerformed
        // TODO add your handling code here:
        int boton = JOptionPane.showConfirmDialog(this, "Se perderá toda la información digitada hasta el momento \n ¿está seguro que desea inicar de nuevo?", "Nueva Remisión", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

        if (boton == JOptionPane.YES_OPTION) {
            limpiarTablaYCampos();
        }
    }//GEN-LAST:event_btnNuevaFacturaActionPerformed

//    public static void main(String args[]) {
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(FacturaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(FacturaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(FacturaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(FacturaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                ComprasView dialog = new ComprasView(new java.awt.Frame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminarProducto;
    private javax.swing.JButton btnGenerarCompra;
    private javax.swing.JButton btnNuevaFactura;
    private com.toedter.calendar.JDateChooser calFechaCompra;
    private javax.swing.JComboBox cbxProveedores;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblDescuento;
    private javax.swing.JLabel lblDescuentoDes;
    private javax.swing.JLabel lblFechaRegistro;
    private javax.swing.JLabel lblFechaVencimiento;
    private javax.swing.JLabel lblSubTotal;
    private javax.swing.JLabel lblSubtotalDes;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblTotalDes;
    private javax.swing.JTextField txtDiasPlazo;
    private javax.swing.JTextField txtFacturaCompra;
    private javax.swing.JTextArea txtObservacion;
    // End of variables declaration//GEN-END:variables
}
