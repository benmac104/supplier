package com.project.hit.view.orderView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.plaf.DimensionUIResource;

import com.project.hit.controller.orderController.OrderController;
import com.project.hit.controller.supplierController.SupplierController;
import com.project.hit.model.orderSystem.Order;
import com.project.hit.model.orderSystem.OrderExistException;
import com.project.hit.model.orderSystem.OrderNotFoundExcption;
import com.project.hit.model.supplierSystem.SupplierNotFoundException;

public class OrderPageView implements OrderView {
	
	private OrderController orderController;
	private SupplierController supplierController;
	private JFrame mainFrame;
	private AddOrderPanel addPanel;
	private EditSearchOrderPanel editPanel;
	private DeleteOrderPanel deletePanel;
	
	public OrderPageView() {
	}

	@Override
	public void start() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setController(OrderController orderController, SupplierController supplierController) {
		this.orderController = orderController;
		this.supplierController = supplierController;
		
	}
	
	private void createAndShowGUI() {
		mainFrame = new JFrame();
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setPreferredSize(new DimensionUIResource(370, 350));
		
        addPanel = new AddOrderPanel();
		editPanel = new EditSearchOrderPanel();
		deletePanel = new DeleteOrderPanel(orderController.getOrders());
		
		addAddPanelListeners();
		addEditPanelListeners();
		addDeletePanelListeners();
		
		JTabbedPane mainPanel = new JTabbedPane(JTabbedPane.TOP);
		mainPanel.addTab("Edit Order", null, editPanel, null);
		mainPanel.addTab("Add Order", null, addPanel, null);
		mainPanel.addTab("Delete Order", null, deletePanel, null);		
		
		mainFrame.add(mainPanel);
		
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}
	
	public void addAddPanelListeners() {
		
		addPanel.AddAddButtonListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					orderController.createOrder(new Order(supplierController.findSupplier(Integer.parseInt(addPanel.GetSupplierNumber())),
							Double.parseDouble(addPanel.GetSupplierNumber()),
							addPanel.getDescription()));
				} catch (ClassNotFoundException | IOException e1) {
					setError(e1.getMessage());
				} catch (SupplierNotFoundException e1) {
					setError("Supplier Not Found.\nPlease try again.");
				} catch (OrderExistException e1) {
					setError("Order already exist.\nPlease try again.");
				} catch (NumberFormatException e1) {
					setError("Price value is invalid.\nPlease user diffrent value.");
				}
			}
		});
		
		addPanel.AddResetButtonListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				addPanel.resetFields();
			}
		});
	}
	
	public void addEditPanelListeners() {
		editPanel.addFindButtonListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					editPanel.setTextToFileds(orderController.searchOrderByNumber(Integer.parseInt(editPanel.getOrderNumberFromUser())));
				} catch (NumberFormatException | OrderNotFoundExcption e1) {
					setError("Order not found");
				}
			}
		});
		editPanel.addResetButtonListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				editPanel.resetFields();
			}
		});
		editPanel.addSaveButtonListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					orderController.editOrder(editPanel.getOrderNumberFromUser(), editPanel.getUpdatedOrder());
				} catch (NumberFormatException | ClassNotFoundException | IOException | OrderNotFoundExcption e1) {
					setError("Error saving the order.\nPlease try again");
				}
			}
		});
	}
	
	public void addDeletePanelListeners() {
		deletePanel.addRemoveActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					orderController.deleteOrder(deletePanel.removeOrder());
					deletePanel.setValues(orderController.getOrders());
					JOptionPane.showMessageDialog(new JFrame(),"Order Deleted!","Succsess",JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e1) {
					setError(e1.getMessage());}
			}
		});
		deletePanel.addRefreshActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				deletePanel.setValues(orderController.getOrders());
			}
		});
	}
	
	/**
	 * Creates and display an Error message to the user.
	 * @param msg - {@link String} Message to display.
	 */
	private void setError(String msg) {
		JOptionPane.showMessageDialog(new JFrame(),msg,"Error",JOptionPane.ERROR_MESSAGE);
	}
}
