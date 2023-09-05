package library.v2_0.view.swing;

import library.v2_0.domain.Book;
import library.v2_0.domain.User;
import library.v2_0.infrastructure.Dispatcher;
import library.v2_0.infrastructure.Model;
import library.v2_0.infrastructure.ModelAndTarget;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public class SwingManagerView extends AbstractSwingView {
  private static final String MANAGER_CONTROLLER = "manager_controller";
  private final Container content = new Container();
  private final SwingManagerView.BookTableModel bookTableModel = new SwingManagerView.BookTableModel();
  private final JTable booksTable = new JTable(bookTableModel);
  private final JLabel isbnLabel = new JLabel("Isbn");
  private final JTextField isbnTextField = new JTextField();
  private final JLabel titleLabel = new JLabel("Title");
  private final JTextField titleTextField = new JTextField();
  private final JLabel authorLabel = new JLabel("Author");
  private final JTextField authorTextField = new JTextField();
  private final JLabel yearLabel = new JLabel("Year");
  private final JTextField yearTextField = new JTextField();
  private final JLabel priceLabel = new JLabel("Price");
  private final JTextField priceTextField = new JTextField();
  private final JLabel quantityLabel = new JLabel("Quantity");
  private final JTextField quantityTextField = new JTextField();
  private final JButton addBookButton = new JButton("Add");

  private final JLabel removeBookIdLabel = new JLabel("User ID");
  private final JTextField removeBookIdTextField = new JTextField();
  private final JButton removeBookButton = new JButton("Remove");

  private final JButton logout = new JButton("Logout");

  private User currentUser;

  public SwingManagerView(JFrame frame, Dispatcher dispatcher) {
    super(frame, dispatcher);
  }

  @Override
  public String update(Model inModel, Model outModel) {
    currentUser = inModel.get("user");

    frame.getContentPane().removeAll();

    JTableHeader booksTableHeader = booksTable.getTableHeader();
    booksTableHeader.setBounds(20, 20, 745, 20);
    booksTable.setBounds(20, 40, 745, 280);
    isbnLabel.setBounds(25, 340, 70, 20);
    isbnTextField.setBounds(85, 340, 100, 20);
    titleLabel.setBounds(215, 340, 70, 20);
    titleTextField.setBounds(275, 340, 100, 20);
    authorLabel.setBounds(405, 340, 70, 20);
    authorTextField.setBounds(435, 340, 100, 20);
    yearLabel.setBounds(565, 340, 70, 20);
    yearTextField.setBounds(595, 340, 100, 20);
    priceLabel.setBounds(25, 340, 70, 20);
    priceTextField.setBounds(85, 340, 100, 20);
    quantityLabel.setBounds(215, 340, 70, 20);
    quantityTextField.setBounds(275, 340, 100, 20);

    addBookButton.setBounds(595, 340, 100, 20);
    removeBookIdLabel.setBounds(25, 405, 100, 20);
    removeBookIdTextField.setBounds(85, 405, 100, 20);
    removeBookButton.setBounds(215, 405, 100, 20);
    logout.setBounds(670, 520, 100, 20);

    content.add(booksTableHeader);
    content.add(booksTable);
    content.add(isbnLabel);
    content.add(isbnTextField);
    content.add(titleLabel);
    content.add(titleTextField);
    content.add(authorLabel);
    content.add(authorTextField);
    content.add(yearLabel);
    content.add(yearTextField);
    content.add(priceLabel);
    content.add(priceTextField);
    content.add(quantityLabel);
    content.add(quantityTextField);

    content.add(addBookButton);
    content.add(removeBookIdLabel);
    content.add(removeBookIdTextField);
    content.add(removeBookButton);
    content.add(logout);

    resetFields();

    frame.getContentPane().add(content);
    frame.revalidate();
    frame.repaint();

    updateUsersTable(inModel);

    return MANAGER_CONTROLLER;
  }
  public void updateUsersTable(Model inModel) {
    Model model = new Model();
    model.put("user", inModel.get("user"));
    model.put("action", "showUsers");
    ModelAndTarget response = dispatcher.dispatchRequest(MANAGER_CONTROLLER, model);
    bookTableModel.setBooks(response.getModel().get("books"));
  }
  private void resetFields() {
    isbnTextField.setText("");
    titleTextField.setText("");
    authorTextField.setText("");
    yearTextField.setText("");
    priceTextField.setText("");
    quantityTextField.setText("");
    removeBookIdTextField.setText("");

  }
  private static class BookTableModel extends AbstractTableModel {

    private final String[] columnNames = {"ID", "Isbn", "Title", "Author", "Year", "Price", "Quantity"};
    private java.util.List<Book> books = Collections.emptyList();

    public void setBooks(List<Book> books) {
      this.books = books;
      fireTableDataChanged();
    }

    @Override
    public String getColumnName(int col) {
      return columnNames[col];
    }

    @Override
    public int getRowCount() {
      return books.size();
    }

    @Override
    public int getColumnCount() {
      return columnNames.length;
    }

    @Override
    public Object getValueAt(int row, int col) {
      switch (col) {
        case 0:
          return books.get(row).getId();
        case 1:
          return books.get(row).getIsbn();
        case 2:
          return books.get(row).getTitle();
        case 4:
          return books.get(row).getAuthor();
        case 5:
          return books.get(row).getYear();
        case 6:
          return books.get(row).getPrice();
        case 7:
          return books.get(row).getCount();
        default:
          throw new IllegalArgumentException();
      }
    }
  }
}
