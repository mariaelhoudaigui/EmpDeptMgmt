package mariaprojt;

import connect.NewHibernateUtil;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pack2.Dept;
import pack2.Emp;

public class FXMLDocumentController implements Initializable {

    @FXML
    private Label label;

    // Employe Tab
    @FXML
    private Button savebutton;
    @FXML
    private Button fermerbutton;
    @FXML
    private Button deletebutton;
    @FXML
    private TextField salairetext;
    @FXML
    private TextField datetext;
    @FXML
    private TextField postetext;
    @FXML
    private TextField depttext;
    @FXML
    private TextField idtext;
    @FXML
    private TextField Nomtext;
    @FXML
    private TextField prenomtext;
    @FXML
    private TableView<Emp> tableemp;
    @FXML
    private TableColumn<Emp, Integer> empID;
    @FXML
    private TableColumn<Emp, String> empPrenom;
    @FXML
    private TableColumn<Emp, String> empNom;
    @FXML
    private TableColumn<Emp, Integer> empSalaire;
    @FXML
    private TableColumn<Emp, String> empPoste;
    @FXML
    private TableColumn<Emp, String> empDatedenaissance;
    @FXML
    private TableColumn<Emp, Dept> empDept;

    // Departement Tab
    @FXML
    private Button savebotton;
    @FXML
    private Button fermerbotton;
    @FXML
    private Button deletebotton;
    @FXML
    private TextField iddepttext;
    @FXML
    private TextField nomtext;
    @FXML
    private TextField localisationtext;
    @FXML
    private Button searchbotton;
    @FXML
    private TableView<Dept> tabledept;
    @FXML
    private TableColumn<Dept, Integer> deptid_dept;
    @FXML
    private TableColumn<Dept, String> deptNom;
    @FXML
    private TableColumn<Dept, String> deptLocalisation;

    
    
    @FXML
    private void fermerFenetre(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous fermer cette interface?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) fermerbutton.getScene().getWindow();
            stage.close();
        }
    }

   
   


//    @FXML
//    private void searchEmp(ActionEvent event) {
//        // Add action here
//    }

    // Departement Tab Actions
    @FXML
    private void savedept(ActionEvent event) {
        String id = iddepttext.getText();
        String nom = nomtext.getText();
        String localisation = localisationtext.getText();

        if (id.isEmpty() || nom.isEmpty() || localisation.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs!");
            alert.showAndWait();
            return;
        }

        SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            Dept dept = new Dept();
            dept.setNom(nom);
            dept.setLocalisation(localisation);

            session.save(dept);
            tx.commit();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Département enregistré avec succès!");
            alert.showAndWait();

            // Fermez la fenêtre après avoir sauvegardé le département
//            Stage stage = (Stage) savebotton.getScene().getWindow();
//            stage.close();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors de l'enregistrement du département: " + e.getMessage());
            alert.showAndWait();
        } finally {
            session.close();
        }
    }
    
   @FXML
   
 public void saveemp() {
    // Création d'une nouvelle session Hibernate
    Session session = NewHibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;

    try {
        // Début de la transaction
        transaction = session.beginTransaction();

        // Création d'une nouvelle instance d'employé avec les données des champs textuels
        Emp employee = new Emp();
        employee.setId(Integer.parseInt(idtext.getText()));
        employee.setNom(Nomtext.getText());
        employee.setPrenom(prenomtext.getText());
        employee.setSalaire(Integer.parseInt(salairetext.getText()));

        // Conversion de la chaîne de texte représentant la date en un objet java.sql.Date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateOfBirth = LocalDate.parse(datetext.getText(), formatter);
        Date sqlDate = Date.valueOf(dateOfBirth);
        employee.setDateDeNaissance(sqlDate);

        employee.setPoste(postetext.getText());

        // Récupération du département correspondant par son ID
        int deptId = Integer.parseInt(depttext.getText());
        Dept department = (Dept) session.get(Dept.class, deptId);
        if (department != null) {
            // Si le département est trouvé, assignez-le à l'employé
            employee.setDept(department);
        } else {
            // Si le département n'est pas trouvé, affichez un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Département non trouvé avec l'ID : " + deptId);
            alert.showAndWait();
            return; // Arrêtez le traitement de la méthode
        }

        // Enregistrement de l'employé dans la base de données
        session.save(employee);

        // Validation de la transaction
        transaction.commit();

        // Affichage d'un message de succès
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Succès");
        successAlert.setHeaderText(null);
        successAlert.setContentText("Employé enregistré avec succès!");
        successAlert.showAndWait();

    } catch (Exception e) {
        if (transaction != null) {
            // Annulation de la transaction en cas d'erreur
            transaction.rollback();
        }
        e.printStackTrace();

        // Affichage d'un message d'erreur
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Erreur");
        errorAlert.setHeaderText(null);
        errorAlert.setContentText("Erreur lors de l'enregistrement de l'employé : " + e.getMessage());
        errorAlert.showAndWait();
    } finally {
        // Fermeture de la session Hibernate
        session.close();
    }
}





   

    @FXML
    private void deleteDept(ActionEvent event) {
        // Récupérer l'ID du département à supprimer en fonction du champ IDDept
        int deptId = Integer.parseInt(iddepttext.getText());

        // Création d'une nouvelle session Hibernate
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            // Début de la transaction
            transaction = session.beginTransaction();

            // Récupération du département à supprimer
            Dept departmentToDelete = (Dept) session.get(Dept.class, deptId);

            if(departmentToDelete != null) {
                // Suppression du département
                session.delete(departmentToDelete);

                // Validation de la transaction
                transaction.commit();

                // Afficher un message de confirmation de suppression
                System.out.println("Département supprimé avec succès !");
            } else {
                // Si aucun département correspondant à l'ID fourni n'est trouvé
                System.out.println("Aucun département trouvé avec l'ID : " + deptId);
            }
        } catch (Exception e) {
            if (transaction != null) {
                // Annulation de la transaction en cas d'erreur
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            // Fermeture de la session Hibernate
            session.close();
        }
    }

    @FXML
    private void deleteEmp(ActionEvent event) {
        // Récupérer l'employé à supprimer en fonction de l'ID fourni dans le champ ID
        int empId = Integer.parseInt(idtext.getText());

        // Création d'une nouvelle session Hibernate
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            // Début de la transaction
            transaction = session.beginTransaction();

            // Récupération de l'employé à supprimer
            Emp employeeToDelete = (Emp) session.get(Emp.class, empId);

            if(employeeToDelete != null) {
                // Suppression de l'employé
                session.delete(employeeToDelete);

                // Validation de la transaction
                transaction.commit();

                // Afficher un message de confirmation de suppression
                System.out.println("Employé supprimé avec succès !");
            } else {
                // Si aucun employé correspondant à l'ID fourni n'est trouvé
                System.out.println("Aucun employé trouvé avec l'ID : " + empId);
            }
        } catch (Exception e) {
            if (transaction != null) {
                // Annulation de la transaction en cas d'erreur
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            // Fermeture de la session Hibernate
            session.close();
        }
    }



    @FXML
private void searchEmp(ActionEvent event) {
    // Récupérer l'ID de l'employé à rechercher
    int empId = Integer.parseInt(idtext.getText());
    
    // Création d'une nouvelle session Hibernate
    Session session = NewHibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;

    try {
        // Début de la transaction
        transaction = session.beginTransaction();

        // Recherche de l'employé en fonction de son ID
        Emp employee = (Emp) session.get(Emp.class, empId);
        
        if(employee != null) {
            // Afficher les informations de l'employé dans une boîte de dialogue d'alerte
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Employé");
            alert.setHeaderText("Informations sur l'employé");
            alert.setContentText("ID: " + employee.getId() + "\n"
                                + "Nom: " + employee.getNom() + "\n"
                                + "Prénom: " + employee.getPrenom() + "\n"
                                + "Salaire: " + employee.getSalaire() + "\n"
                                + "Date de naissance: " + employee.getDateDeNaissance() + "\n"
                                + "Poste: " + employee.getPoste());
            alert.showAndWait();
        } else {
            // Si aucun employé correspondant à l'ID fourni n'est trouvé
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Aucun employé trouvé avec l'ID : " + empId);
            alert.showAndWait();
        }
        
        // Validation de la transaction
        transaction.commit();
    } catch (Exception e) {
        if (transaction != null) {
            // Annulation de la transaction en cas d'erreur
            transaction.rollback();
        }
        e.printStackTrace();
    } finally {
        // Fermeture de la session Hibernate
        session.close();
    }
}
  @FXML
  private void searchDept(ActionEvent event) {
    // Récupérer l'ID du département à rechercher
    int deptId = Integer.parseInt(iddepttext.getText());
    
    // Création d'une nouvelle session Hibernate
    Session session = NewHibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;

    try {
        // Début de la transaction
        transaction = session.beginTransaction();

        // Recherche du département en fonction de son ID
        Dept department = (Dept) session.get(Dept.class, deptId);
        
        if(department != null) {
            // Afficher les informations du département dans une boîte de dialogue d'alerte
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Département");
            alert.setHeaderText("Informations sur le département");
            alert.setContentText("ID: " + department.getId() + "\n"
                                + "Nom: " + department.getNom() + "\n"
                                + "Localisation: " + department.getLocalisation());
            alert.showAndWait();
        } else {
            // Si aucun département correspondant à l'ID fourni n'est trouvé
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Aucun département trouvé avec l'ID : " + deptId);
            alert.showAndWait();
        }
        
        // Validation de la transaction
        transaction.commit();
    } catch (Exception e) {
        if (transaction != null) {
            // Annulation de la transaction en cas d'erreur
            transaction.rollback();
        }
        e.printStackTrace();
    } finally {
        // Fermeture de la session Hibernate
        session.close();
    }
}
 
  @Override
  @FXML
    public void initialize(URL url,ResourceBundle rb) {
       afficherDept();
      
       
    }
    
  private void afficherDept() {
    List<Dept> deptList = Dept.listDept();

    // Créer une liste observable à partir de la liste des départements
    ObservableList<Dept> observableDeptList = FXCollections.observableArrayList(deptList);

    // Ajouter les départements à la table
    tabledept.setItems(observableDeptList);

    // Définir les valeurs des colonnes
    deptid_dept.setCellValueFactory(new PropertyValueFactory<>("id"));
    deptNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
    deptLocalisation.setCellValueFactory(new PropertyValueFactory<>("localisation"));
}
  
  



 

}
