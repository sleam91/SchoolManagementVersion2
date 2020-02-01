package view;

import schoolmanagementservices.CourseManagement;

public class CourseMenu {

    UI ui;
    CourseManagement course;

    public CourseMenu(UI ui, CourseManagement course) {
        this.ui = ui;
        this.course=course;
    }

    public void courseMenu() {
        while (true) {
            ui.outputString("---------------------");
            ui.outputString("Course Menu");
            ui.outputString("---------------------");
            ui.outputString("1. Find Course by Name/ID");
            ui.outputString("2. Show All Courses");
            ui.outputString("3. Add New Course");
            ui.outputString("4. Update Course Name");
            ui.outputString("5. Add Teachers to Course");
            ui.outputString("6. Remove Teacher from Course");
            ui.outputString("7. Add/Change Program from Course");
            ui.outputString("8. Remove Program from Course");
            ui.outputString("9. Change Start Date of Course");
            ui.outputString("10. Change End Date of Course");
            ui.outputString("11. Delete Course");
            ui.outputString("0. Back to main menu");
            ui.outputString("-----------------");
            int choice = ui.takeChoiceInput(11);
            ui.clear();
            switch (choice) {

                case 1:
                    ui.outputString("Enter Name or ID of Course:");
                    course.findByNameOrID();
                    break;
                case 2:
                    course.showAll();
                    break;
                case 3:
                    course.add();
                    break;
                case 4:
                    course.update("updateName");
                    break;
                case 5:
                    course.update("addTeachers");
                    break;
                case 6:
                    course.update("removeTeacher");
                    break;
                case 7:
                    course.update("addProgram");
                    break;
                case 8:
                    course.update("removeProgram");
                    break;
                case 9:
                    course.update("updateStartDate");
                    break;
                case 10:
                    course.update("updateEndDate");
                    break;
                case 11:
                    course.delete();
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
