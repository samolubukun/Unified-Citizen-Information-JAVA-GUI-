import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class CitizenInformationSystem {
    private JFrame mainFrame;
    private JLabel descriptionLabel;

    private JButton nimcButton;
    private JButton frscButton;
    private JButton inecButton;
    private JButton immigrationButton;
    private JButton unifiedRecordsButton;

    private final Map<String, Citizen> citizens; // Map to store citizen information

    public CitizenInformationSystem() {
        citizens = new HashMap<>(); // Initialize the map

        prepareGUI();
    }

    private void prepareGUI() {
        // Create the main frame
        mainFrame = new JFrame("Citizen Information System");
        mainFrame.setSize(1500, 800);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridLayout(2, 1));

        String descriptionText = "<html>Unified Citizen Information System: A framework for integrating citizen records from NIMC, FRSC, INEC, and Immigration.<br>"
                + "Enter data or edit data in agency-specific windows, view unified records in real-time.<br>"
                + "Simplify record management and enhance data accessibility.</html>";
        descriptionLabel = new JLabel(descriptionText);


        // Create buttons
        nimcButton = new JButton("NIMC");
        frscButton = new JButton("FRSC");
        inecButton = new JButton("INEC");
        immigrationButton = new JButton("Immigration");
        unifiedRecordsButton = new JButton("Unified Records");

        // Add buttons to the main frame
        mainFrame.add(descriptionLabel);
        mainFrame.add(nimcButton);
        mainFrame.add(frscButton);
        mainFrame.add(inecButton);
        mainFrame.add(immigrationButton);
        mainFrame.add(unifiedRecordsButton);


        // Register action listeners for the buttons
        nimcButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openNIMCWindow();
            }
        });

        frscButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openFRSCWindow();
            }
        });

        inecButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openINECWindow();
            }
        });

        immigrationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openImmigrationWindow();
            }
        });

        unifiedRecordsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openUnifiedRecordsWindow();
            }
        });

        // Display the main frame
        mainFrame.setVisible(true);
    }

    // Declare a StringBuilder variable to store the appended text for the windows
    private final StringBuilder nimcText = new StringBuilder();
    private final StringBuilder frscText = new StringBuilder();
    private final StringBuilder inecText = new StringBuilder();
    private final StringBuilder immigrationText = new StringBuilder();

    private void openNIMCWindow() {
        JFrame nimcFrame = new JFrame("NIMC Window");
        nimcFrame.setSize(1500, 650);
        nimcFrame.setLayout(new BorderLayout());

        // Create labels and text fields
        JLabel ninLabel = new JLabel("NIN:");
        JTextField ninField = new JTextField(20);
        JLabel nameLabel = new JLabel("Full Name:");
        JTextField nameField = new JTextField(20);
        JLabel dobLabel = new JLabel("Date of Birth:");
        JTextField dobField = new JTextField(20);
        JLabel genderLabel = new JLabel("Gender:");
        JTextField genderField = new JTextField(20);
        JLabel addressLabel = new JLabel("Address:");
        JTextField addressField = new JTextField(20);
        JLabel contactLabel = new JLabel("Contact Information:");
        JTextField contactField = new JTextField(20);

        // Create a text area to display the user inputs
        JTextArea textArea = new JTextArea(10, 50);
        textArea.setEditable(false);

        // Set the stored text to the text area
        textArea.setText(nimcText.toString());

        // Create a scroll pane and add the text area to it
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Add the scroll pane to the nimcFrame
        nimcFrame.add(scrollPane, BorderLayout.CENTER);

        // Create an "Add" button
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");

        // Create a panel for the labels and text fields
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(7, 2));
        inputPanel.add(ninLabel);
        inputPanel.add(ninField);
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(dobLabel);
        inputPanel.add(dobField);
        inputPanel.add(genderLabel);
        inputPanel.add(genderField);
        inputPanel.add(addressLabel);
        inputPanel.add(addressField);
        inputPanel.add(contactLabel);
        inputPanel.add(contactField);

        // Create a panel for the button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);

        // Add components to the nimcFrame
        nimcFrame.add(inputPanel, BorderLayout.NORTH);
        nimcFrame.add(buttonPanel, BorderLayout.SOUTH);

        // Register an action listener for the "Add" button
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nin = ninField.getText();
                String name = nameField.getText();
                String dob = dobField.getText();
                String gender = genderField.getText();
                String address = addressField.getText();
                String contact = contactField.getText();

                // Append the user inputs to the StringBuilder variable
                nimcText.append("NIN: " + nin + "\n");
                nimcText.append("Name: " + name + "\n");
                nimcText.append("DOB: " + dob + "\n");
                nimcText.append("Gender: " + gender + "\n");
                nimcText.append("Address: " + address + "\n");
                nimcText.append("Contact: " + contact + "\n");
                nimcText.append("---------------------\n");

                // Set the updated text to the text area
                textArea.setText(nimcText.toString());

                // Create a Citizen object and store the information
                Citizen citizen = new Citizen(nin, name, dob, gender, address, contact);
                citizens.put(nin, citizen); // Add citizen to the map

                // Clear the input fields
                clearFields(inputPanel);

                // Display the unified records
                displayUnifiedRecords();
            }
        });

        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String editNIN = JOptionPane.showInputDialog(mainFrame, "Enter the NIN of the citizen you want to edit:");

                // Retrieve the existing citizen from the map using the entered NIN
                Citizen citizen = citizens.get(editNIN);

                if (citizen != null) {
                    // Create input dialogs for each field and initialize them with existing values
                    String updatedName = JOptionPane.showInputDialog(nimcFrame, "Enter updated Full Name:", citizen.getName());
                    String updatedDOB = JOptionPane.showInputDialog(nimcFrame, "Enter updated Date of Birth:", citizen.getDob());
                    String updatedGender = JOptionPane.showInputDialog(nimcFrame, "Enter updated Gender:", citizen.getGender());
                    String updatedAddress = JOptionPane.showInputDialog(nimcFrame, "Enter updated Address:", citizen.getAddress());
                    String updatedContact = JOptionPane.showInputDialog(nimcFrame, "Enter updated Contact Information:", citizen.getContact());

                    // Update the citizen object with the new information
                    if (updatedName != null && !updatedName.isEmpty()) {
                        citizen.setName(updatedName);
                    }
                    if (updatedDOB != null && !updatedDOB.isEmpty()) {
                        citizen.setDob(updatedDOB);
                    }
                    if (updatedGender != null && !updatedGender.isEmpty()) {
                        citizen.setGender(updatedGender);
                    }
                    if (updatedAddress != null && !updatedAddress.isEmpty()) {
                        citizen.setAddress(updatedAddress);
                    }
                    if (updatedContact != null && !updatedContact.isEmpty()) {
                        citizen.setContact(updatedContact);
                    }

                    // Update the nimcText StringBuilder with the updated information
                    nimcText.setLength(0); // Clear the StringBuilder
                    nimcText.append("NIN: ").append(citizen.getNin()).append("\n");
                    nimcText.append("Name: ").append(citizen.getName()).append("\n");
                    nimcText.append("DOB: ").append(citizen.getDob()).append("\n");
                    nimcText.append("Gender: ").append(citizen.getGender()).append("\n");
                    nimcText.append("Address: ").append(citizen.getAddress()).append("\n");
                    nimcText.append("Contact: ").append(citizen.getContact()).append("\n");
                    nimcText.append("---------------------\n");

                    // Update the text area with the updated information
                    textArea.setText(nimcText.toString());

                    // Update the unified records window text area
                    displayUnifiedRecords();
                } else {
                    JOptionPane.showMessageDialog(nimcFrame, "No citizen found with the entered NIN.");
                }
            }
        });



        // Display the NIMC window
        nimcFrame.setVisible(true);
    }

    private void openFRSCWindow() {
        JFrame frscFrame = new JFrame("FRSC Window");
        frscFrame.setSize(1500, 650);
        frscFrame.setLayout(new BorderLayout());

        // Create labels and text fields
        JLabel ninLabel = new JLabel("NIN:");
        JTextField ninField = new JTextField(20);
        JLabel licenseNumberLabel = new JLabel("Driver's License Number:");
        JTextField licenseNumberField = new JTextField(20);
        JLabel licenseClassLabel = new JLabel("License Class:");
        JTextField licenseClassField = new JTextField(20);
        JLabel expiryDateLabel = new JLabel("Expiration Date:");
        JTextField expiryDateField = new JTextField(20);

        // Create a text area to display the user inputs
        JTextArea textArea = new JTextArea(10, 50);
        textArea.setEditable(false);

        // Set the stored text to the text area
        textArea.setText(frscText.toString());

        // Create a scroll pane and add the text area to it
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Add the scroll pane to the frscFrame
        frscFrame.add(scrollPane, BorderLayout.CENTER);

        // Create an "Add" button
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");

        // Create a panel for the labels and text fields
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));
        inputPanel.add(ninLabel);
        inputPanel.add(ninField);
        inputPanel.add(licenseNumberLabel);
        inputPanel.add(licenseNumberField);
        inputPanel.add(licenseClassLabel);
        inputPanel.add(licenseClassField);
        inputPanel.add(expiryDateLabel);
        inputPanel.add(expiryDateField);

        // Create a panel for the button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);

        // Add components to the frscFrame
        frscFrame.add(inputPanel, BorderLayout.NORTH);
        frscFrame.add(buttonPanel, BorderLayout.SOUTH);

        // Register an action listener for the "Add" button
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nin = ninField.getText();
                String licenseNumber = licenseNumberField.getText();
                String licenseClass = licenseClassField.getText();
                String expiryDate = expiryDateField.getText();

                // Retrieve the existing citizen from the map or create a new one
                Citizen citizen = citizens.getOrDefault(nin, new Citizen(nin));
                citizen.setLicenseNumber(licenseNumber);
                citizen.setLicenseClass(licenseClass);
                citizen.setExpiryDate(expiryDate);
                citizens.put(nin, citizen); // Update citizen in the map

                // Append the user inputs to the StringBuilder variable
                frscText.append("NIN: " + nin + "\n");
                frscText.append("License Number: " + licenseNumber + "\n");
                frscText.append("License Class: " + licenseClass + "\n");
                frscText.append("Expiration Date: " + expiryDate + "\n");
                frscText.append("---------------------\n");

                // Set the updated text to the text area
                textArea.setText(frscText.toString());

                // Clear the input fields
                clearFields(inputPanel);

                // Display the unified records
                displayUnifiedRecords();
            }
        });
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String editNIN = JOptionPane.showInputDialog(mainFrame, "Enter the NIN of the citizen you want to edit:");

                // Retrieve the existing citizen from the map using the entered NIN
                Citizen citizen = citizens.get(editNIN);

                if (citizen != null) {
                    // Create input dialogs for each field and initialize them with existing values
                    String updatedLicenseNumber = JOptionPane.showInputDialog(frscFrame, "Enter updated Driver's License Number:", citizen.getLicenseNumber());
                    String updatedLicenseClass = JOptionPane.showInputDialog(frscFrame, "Enter updated License Class:", citizen.getLicenseClass());
                    String updatedExpiryDate = JOptionPane.showInputDialog(frscFrame, "Enter updated Expiration Date:", citizen.getExpiryDate());

                    // Update the citizen object with the new information
                    if (updatedLicenseNumber != null && !updatedLicenseNumber.isEmpty()) {
                        citizen.setLicenseNumber(updatedLicenseNumber);
                    }
                    if (updatedLicenseClass != null && !updatedLicenseClass.isEmpty()) {
                        citizen.setLicenseClass(updatedLicenseClass);
                    }
                    if (updatedExpiryDate != null && !updatedExpiryDate.isEmpty()) {
                        citizen.setExpiryDate(updatedExpiryDate);
                    }

                    // Update the frscText StringBuilder with the updated information
                    frscText.setLength(0); // Clear the StringBuilder
                    frscText.append("NIN: ").append(citizen.getNin()).append("\n");
                    frscText.append("License Number: ").append(citizen.getLicenseNumber()).append("\n");
                    frscText.append("License Class: ").append(citizen.getLicenseClass()).append("\n");
                    frscText.append("Expiration Date: ").append(citizen.getExpiryDate()).append("\n");
                    frscText.append("---------------------\n");

                    // Update the text area with the updated information
                    textArea.setText(frscText.toString());

                    // Update the unified records window text area
                    displayUnifiedRecords();
                } else {
                    JOptionPane.showMessageDialog(frscFrame, "No citizen found with the entered NIN.");
                }
            }
        });


        // Display the FRSC window
        frscFrame.setVisible(true);
    }

    private void openINECWindow() {
        JFrame inecFrame = new JFrame("INEC Window");
        inecFrame.setSize(1500, 650);
        inecFrame.setLayout(new BorderLayout());

        // Create labels and text fields
        JLabel ninLabel = new JLabel("NIN:");
        JTextField ninField = new JTextField(20);
        JLabel voterStatusLabel = new JLabel("Voter Registration Status:");
        JTextField voterStatusField = new JTextField(20);
        JLabel pollingUnitLabel = new JLabel("Polling Unit:");
        JTextField pollingUnitField = new JTextField(20);
        JLabel voterIdLabel = new JLabel("Voter ID Card Number:");
        JTextField voterIdField = new JTextField(20);

        // Create a text area to display the user inputs
        JTextArea textArea = new JTextArea(10, 50);
        textArea.setEditable(false);

        // Set the stored text to the text area
        textArea.setText(inecText.toString());

        // Create a scroll pane and add the text area to it
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Add the scroll pane to the inecFrame
        inecFrame.add(scrollPane, BorderLayout.CENTER);

        // Create an "Add" button
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");

        // Create a panel for the labels and text fields
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));
        inputPanel.add(ninLabel);
        inputPanel.add(ninField);
        inputPanel.add(voterStatusLabel);
        inputPanel.add(voterStatusField);
        inputPanel.add(pollingUnitLabel);
        inputPanel.add(pollingUnitField);
        inputPanel.add(voterIdLabel);
        inputPanel.add(voterIdField);

        // Create a panel for the button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);

        // Add components to the inecFrame
        inecFrame.add(inputPanel, BorderLayout.NORTH);
        inecFrame.add(buttonPanel, BorderLayout.SOUTH);

        // Register an action listener for the "Add" button
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nin = ninField.getText();
                String voterStatus = voterStatusField.getText();
                String pollingUnit = pollingUnitField.getText();
                String voterId = voterIdField.getText();

                // Retrieve the existing citizen from the map or create a new one
                Citizen citizen = citizens.getOrDefault(nin, new Citizen(nin));
                citizen.setVoterStatus(voterStatus);
                citizen.setPollingUnit(pollingUnit);
                citizen.setVoterId(voterId);
                citizens.put(nin, citizen); // Update citizen in the map

                // Append the user inputs to the StringBuilder variable
                inecText.append("NIN: " + nin + "\n");
                inecText.append("Voter Registration Status: " + voterStatus + "\n");
                inecText.append("Polling Unit: " + pollingUnit + "\n");
                inecText.append("Voter ID Card Number: " + voterId + "\n");
                inecText.append("---------------------\n");

                // Set the updated text to the text area
                textArea.setText(inecText.toString());


                // Clear the input fields
                clearFields(inputPanel);

                // Display the unified records
                displayUnifiedRecords();
            }
        });
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String editNIN = JOptionPane.showInputDialog(mainFrame, "Enter the NIN of the citizen you want to edit:");

                // Retrieve the existing citizen from the map using the entered NIN
                Citizen citizen = citizens.get(editNIN);

                if (citizen != null) {
                    // Create input dialogs for each field and initialize them with existing values
                    String updatedVoterStatus = JOptionPane.showInputDialog(inecFrame, "Enter updated Voter Registration Status:", citizen.getVoterStatus());
                    String updatedPollingUnit = JOptionPane.showInputDialog(inecFrame, "Enter updated Polling Unit:", citizen.getPollingUnit());
                    String updatedVoterId = JOptionPane.showInputDialog(inecFrame, "Enter updated Voter ID Card Number:", citizen.getVoterId());

                    // Update the citizen object with the new information
                    if (updatedVoterStatus != null && !updatedVoterStatus.isEmpty()) {
                        citizen.setVoterStatus(updatedVoterStatus);
                    }
                    if (updatedPollingUnit != null && !updatedPollingUnit.isEmpty()) {
                        citizen.setPollingUnit(updatedPollingUnit);
                    }
                    if (updatedVoterId != null && !updatedVoterId.isEmpty()) {
                        citizen.setVoterId(updatedVoterId);
                    }

                    // Update the inecText StringBuilder with the updated information
                    inecText.setLength(0); // Clear the StringBuilder
                    inecText.append("NIN: ").append(citizen.getNin()).append("\n");
                    inecText.append("Voter Registration Status: ").append(citizen.getVoterStatus()).append("\n");
                    inecText.append("Polling Unit: ").append(citizen.getPollingUnit()).append("\n");
                    inecText.append("Voter ID Card Number: ").append(citizen.getVoterId()).append("\n");
                    inecText.append("---------------------\n");

                    // Update the text area with the updated information
                    textArea.setText(inecText.toString());

                    // Update the unified records window text area
                    displayUnifiedRecords();
                } else {
                    JOptionPane.showMessageDialog(inecFrame, "No citizen found with the entered NIN.");
                }
            }
        });


        // Display the INEC window
        inecFrame.setVisible(true);
    }

    private void openImmigrationWindow() {
        JFrame immigrationFrame = new JFrame("Immigration Window");
        immigrationFrame.setSize(1500, 650);
        immigrationFrame.setLayout(new BorderLayout());

        // Create labels and text fields
        JLabel ninLabel = new JLabel("NIN:");
        JTextField ninField = new JTextField(20);
        JLabel passportNumberLabel = new JLabel("Passport Number:");
        JTextField passportNumberField = new JTextField(20);
        JLabel issueDateLabel = new JLabel("Issue Date:");
        JTextField issueDateField = new JTextField(20);
        JLabel expiryDateLabel = new JLabel("Expiry Date:");
        JTextField expiryDateField = new JTextField(20);
        JLabel travelHistoryLabel = new JLabel("Travel History:");
        JTextField travelHistoryField = new JTextField(20);

        // Create a text area to display the user inputs
        JTextArea textArea = new JTextArea(10, 50);
        textArea.setEditable(false);

        // Set the stored text to the text area
        textArea.setText(immigrationText.toString());

        // Create a scroll pane and add the text area to it
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Add the scroll pane to the immigrationFrame
        immigrationFrame.add(scrollPane, BorderLayout.CENTER);

        // Create an "Add" button
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");


        // Create a panel for the labels and text fields
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2));
        inputPanel.add(ninLabel);
        inputPanel.add(ninField);
        inputPanel.add(passportNumberLabel);
        inputPanel.add(passportNumberField);
        inputPanel.add(issueDateLabel);
        inputPanel.add(issueDateField);
        inputPanel.add(expiryDateLabel);
        inputPanel.add(expiryDateField);
        inputPanel.add(travelHistoryLabel);
        inputPanel.add(travelHistoryField);

        // Create a panel for the button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);

        // Add components to the immigrationFrame
        immigrationFrame.add(inputPanel, BorderLayout.NORTH);
        immigrationFrame.add(buttonPanel, BorderLayout.SOUTH);

        // Register an action listener for the "Add" button
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nin = ninField.getText();
                String passportNumber = passportNumberField.getText();
                String issueDate = issueDateField.getText();
                String expiryDate = expiryDateField.getText();
                String travelHistory = travelHistoryField.getText();

                // Retrieve the existing citizen from the map or create a new one
                Citizen citizen = citizens.getOrDefault(nin, new Citizen(nin));
                citizen.setPassportNumber(passportNumber);
                citizen.setIssueDate(issueDate);
                citizen.setExpiryDate(expiryDate);
                citizen.setTravelHistory(travelHistory);
                citizens.put(nin, citizen); // Update citizen in the map


                // Append the user inputs to the StringBuilder variable
                immigrationText.append("NIN: " + nin + "\n");
                immigrationText.append("Passport Number: " + passportNumber + "\n");
                immigrationText.append("Issue Date: " + issueDate + "\n");
                immigrationText.append("Expiry Date: " + expiryDate + "\n");
                immigrationText.append("Travel History: " + travelHistory + "\n");
                immigrationText.append("---------------------\n");

                // Set the updated text to the text area
                textArea.setText(immigrationText.toString());

                // Clear the input fields
                clearFields(inputPanel);

                // Display the unified records
                displayUnifiedRecords();
            }
        });

        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String editNIN = JOptionPane.showInputDialog(mainFrame, "Enter the NIN of the citizen you want to edit:");

                // Retrieve the existing citizen from the map using the entered NIN
                Citizen citizen = citizens.get(editNIN);

                if (citizen != null) {
                    // Create input dialogs for each field and initialize them with existing values
                    String updatedPassportNumber = JOptionPane.showInputDialog(immigrationFrame, "Enter updated Passport Number:", citizen.getPassportNumber());
                    String updatedIssueDate = JOptionPane.showInputDialog(immigrationFrame, "Enter updated Issue Date:", citizen.getIssueDate());
                    String updatedExpiryDate = JOptionPane.showInputDialog(immigrationFrame, "Enter updated Expiry Date:", citizen.getExpiryDate());
                    String updatedTravelHistory = JOptionPane.showInputDialog(immigrationFrame, "Enter updated Travel History:", citizen.getTravelHistory());

                    // Update the citizen object with the new information
                    if (updatedPassportNumber != null && !updatedPassportNumber.isEmpty()) {
                        citizen.setPassportNumber(updatedPassportNumber);
                    }
                    if (updatedIssueDate != null && !updatedIssueDate.isEmpty()) {
                        citizen.setIssueDate(updatedIssueDate);
                    }
                    if (updatedExpiryDate != null && !updatedExpiryDate.isEmpty()) {
                        citizen.setExpiryDate(updatedExpiryDate);
                    }
                    if (updatedTravelHistory != null && !updatedTravelHistory.isEmpty()) {
                        citizen.setTravelHistory(updatedTravelHistory);
                    }

                    // Update the immigrationText StringBuilder with the updated information
                    immigrationText.setLength(0); // Clear the StringBuilder
                    immigrationText.append("NIN: ").append(citizen.getNin()).append("\n");
                    immigrationText.append("Passport Number: ").append(citizen.getPassportNumber()).append("\n");
                    immigrationText.append("Issue Date: ").append(citizen.getIssueDate()).append("\n");
                    immigrationText.append("Expiry Date: ").append(citizen.getExpiryDate()).append("\n");
                    immigrationText.append("Travel History: ").append(citizen.getTravelHistory()).append("\n");
                    immigrationText.append("---------------------\n");

                    // Update the text area with the updated information
                    textArea.setText(immigrationText.toString());

                    // Update the unified records window text area
                    displayUnifiedRecords();
                } else {
                    JOptionPane.showMessageDialog(immigrationFrame, "No citizen found with the entered NIN.");
                }
            }
        });


        // Display the Immigration window
        immigrationFrame.setVisible(true);
    }

    private void openUnifiedRecordsWindow() {
        JFrame unifiedRecordsFrame = new JFrame("Unified Records Window");
        unifiedRecordsFrame.setSize(1500, 800);

        // Create a text area for displaying the unified records
        JTextArea unifiedRecordsArea = new JTextArea(10, 50);
        unifiedRecordsArea.setEditable(false);

        // Create a scroll pane and add the text area to it
        JScrollPane scrollPane = new JScrollPane(unifiedRecordsArea);

        // Add the scroll pane to the unifiedRecordsFrame
        unifiedRecordsFrame.add(scrollPane);

        // Display the Unified Records window
        unifiedRecordsFrame.setVisible(true);

        // Display the unified records immediately after opening the window
        displayUnifiedRecords(unifiedRecordsArea);
    }


    private void clearFields(JPanel panel) {
        Component[] components = panel.getComponents();
        for (Component component : components) {
            if (component instanceof JTextField textField) {
                textField.setText("");
            }
        }
    }

    private void displayUnifiedRecords(JTextArea textArea) {
        textArea.setText(""); // Clear the text area

        for (Citizen citizen : citizens.values()) {
            textArea.append("NIN: " + citizen.getNin() + "\n");
            textArea.append("Name: " + citizen.getName() + "\n");
            textArea.append("DOB: " + citizen.getDob() + "\n");
            textArea.append("Gender: " + citizen.getGender() + "\n");
            textArea.append("Address: " + citizen.getAddress() + "\n");
            textArea.append("Contact: " + citizen.getContact() + "\n");
            textArea.append("License Number: " + citizen.getLicenseNumber() + "\n");
            textArea.append("License Class: " + citizen.getLicenseClass() + "\n");
            textArea.append("Expiration Date: " + citizen.getExpiryDate() + "\n");
            textArea.append("Voter Registration Status: " + citizen.getVoterStatus() + "\n");
            textArea.append("Polling Unit: " + citizen.getPollingUnit() + "\n");
            textArea.append("Voter ID Card Number: " + citizen.getVoterId() + "\n");
            textArea.append("Passport Number: " +citizen.getPassportNumber() + "\n");
            textArea.append("Issue Date: " + citizen.getIssueDate() + "\n");
            textArea.append("Expiry Date: " + citizen.getExpiryDate() + "\n");
            textArea.append("Travel History: " + citizen.getTravelHistory() + "\n");
            textArea.append("---------------------\n");
        }
    }

    private void displayUnifiedRecords() {
        JFrame frame = getFrameByName();
        if (frame != null) {
            JTextArea textArea = (JTextArea) frame.getContentPane().getComponent(0);
            displayUnifiedRecords(textArea);
        }
    }

    private JFrame getFrameByName() {
        for (Window window : Window.getWindows()) {
            if (window instanceof JFrame && window.getName().equals("Unified Records Window")) {
                return (JFrame) window;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        CitizenInformationSystem cis = new CitizenInformationSystem();
    }
}

class Citizen {
    private String nin;
    private String name;
    private String dob;
    private String gender;
    private String address;
    private String contact;
    private String licenseNumber;
    private String licenseClass;
    private String expiryDate;
    private String voterStatus;
    private String pollingUnit;
    private String voterId;
    private String passportNumber;
    private String issueDate;
    private String travelHistory;

    public Citizen(String nin) {
        this.nin = nin;
    }

    public Citizen(String nin, String name, String dob, String gender, String address, String contact) {
        this.nin = nin;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.contact = contact;
    }

    // Getters and Setters

    public void setNin(String nin) {
        this.nin = nin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
    public String getNin() {
        return nin;
    }

    public String getName() {
        return name;
    }

    public String getDob() {
        return dob;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String getContact() {
        return contact;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getLicenseClass() {
        return licenseClass;
    }

    public void setLicenseClass(String licenseClass) {
        this.licenseClass = licenseClass;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getVoterStatus() {
        return voterStatus;
    }

    public void setVoterStatus(String voterStatus) {
        this.voterStatus = voterStatus;
    }

    public String getPollingUnit() {
        return pollingUnit;
    }

    public void setPollingUnit(String pollingUnit) {
        this.pollingUnit = pollingUnit;
    }

    public String getVoterId() {
        return voterId;
    }

    public void setVoterId(String voterId) {
        this.voterId = voterId;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getTravelHistory() {
        return travelHistory;
    }

    public void setTravelHistory(String travelHistory) {
        this.travelHistory = travelHistory;
    }
}
