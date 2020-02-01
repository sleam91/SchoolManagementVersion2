package view;

import schoolmanagementservices.ProgramManagement;

public class ProgramMenu {

    UI ui;
    ProgramManagement program;

    public ProgramMenu(UI ui, ProgramManagement program) {
        this.ui = ui;
        this.program = program;
    }

    public void programMenu() {
        while (true) {
            ui.clear();
            ui.outputString("---------------------");
            ui.outputString("Program Menu");
            ui.outputString("---------------------");
            ui.outputString("1. Find Program by Name/ID");
            ui.outputString("2. Show All Programs");
            ui.outputString("3. Add New Program");
            ui.outputString("4. Update Program Name");
            ui.outputString("5. Add Students to Program");
            ui.outputString("6. Remove Student from Program");
            ui.outputString("7. Add Courses to Program");
            ui.outputString("8. Remove Course from Program");
            ui.outputString("9. Change Start Date of Program");
            ui.outputString("10. Change End Date of Program");
            ui.outputString("11. Delete Program");
            ui.outputString("0. Back to main menu");
            ui.outputString("-----------------");

            int choice = ui.takeChoiceInput(11);
            ui.clear();
            switch (choice) {

                case 1:
                    ui.outputString("Enter Name or ID of Program:");
                    program.findByNameOrID();
                    break;
                case 2:
                    program.showAll();
                    break;
                case 3:
                    program.add();
                    break;
                case 4:
                    program.update("updateName");
                    break;
                case 5:
                    program.update("addStudents");
                    break;
                case 6:
                    program.update("removeStudent");
                    break;
                case 7:
                    program.update("addCourses");
                    break;
                case 8:
                    program.update("removeCourse");
                    break;
                case 9:
                    program.update("updateStartDate");
                    break;
                case 10:
                    program.update("updateEndDate");
                    break;
                case 11:
                    program.delete();
                    break;
                case 0:
                    return;

            }

            if (choice != 5 && choice != 7) {
                ui.pressEnterToContinue();
            }
            ui.clear();
        }
    }

}
