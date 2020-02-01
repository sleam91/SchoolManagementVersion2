package view;

import schoolmanagementservices.StudentManagement;


public class StudentMenu {
    
        UI ui;
        StudentManagement student;

    public StudentMenu(UI ui,StudentManagement student) {
        this.ui = ui;
        this.student=student;
    }
        
    
        public void studentMenu() {
        while (true) {
            ui.outputString("---------------------");
            ui.outputString("Student Menu");
            ui.outputString("---------------------");
            ui.outputString("1. Find Student by Name/ID");
            ui.outputString("2. Show All Students");
            ui.outputString("3. Add New Student");
            ui.outputString("4. Update Student Name");
            ui.outputString("5. Add/Change Program from Student");
            ui.outputString("6. Remove Program from Student");
            ui.outputString("7. Delete Student");
            ui.outputString("0. Back to main menu");
            ui.outputString("-----------------");

            int choice = ui.takeChoiceInput(7);
            ui.clear();
            switch (choice) {

                case 1:
                    ui.outputString("Enter Name or ID of Student:");
                    student.findByNameOrID();
                    break;
                case 2:
                    student.showAll();
                    break;
                case 3:
                    String s=controlInput();
                    student.add();
                    break;
                case 4:
                    student.update("updateName");
                    break;
                case 5:
                    student.update("addProgram");
                    break;
                case 6:
                    student.update("removeProgram");
                    break;
                case 7:
                    student.delete();
                    break;
                case 0:
                    return;
            }

            if (choice != 5) {
                ui.pressEnterToContinue();
            }

            ui.clear();
        }
    }
        
        private String controlInput(){return null;}

}
