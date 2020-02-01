package view;

import schoolmanagementservices.TeacherManagement;

public class TeacherMenu {

    UI ui;
    TeacherManagement teacher;

    public TeacherMenu(UI ui, TeacherManagement teacher) {
        this.ui = ui;
        this.teacher=teacher;
    }

    public void teacherMenu() {
        while (true) {

            ui.outputString("---------------------");
            ui.outputString("Teacher Menu");
            ui.outputString("---------------------");
            ui.outputString("1. Find Teacher by Name/ID");
            ui.outputString("2. Show All Teachers");
            ui.outputString("3. Add New Teacher");
            ui.outputString("4. Update Teacher Name");
            ui.outputString("5. Add Courses to Teacher");
            ui.outputString("6. Remove Course from Teacher");
            ui.outputString("7. Delete Teacher");
            ui.outputString("0. Back to main menu");
            ui.outputString("-----------------");
            int choice = ui.takeChoiceInput(7);
            ui.clear();
            switch (choice) {
                case 1:
                    ui.outputString("Enter Name or ID of Teacher:");
                    teacher.findByNameOrID();
                    break;
                case 2:
                    teacher.showAll();
                    break;
                case 3:
                    teacher.add();
                    break;
                case 4:
                    teacher.update("updateName");
                    break;
                case 5:
                    teacher.update("addCourses");
                    break;
                case 6:
                    teacher.update("removeCourse");
                    break;
                case 7:
                    teacher.delete();
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

}
