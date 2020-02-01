package view;

public class MainMenu {

    UI ui;
    TeacherMenu teacherMenu;
    CourseMenu courseMenu;
    StudentMenu studentMenu;
    ProgramMenu programMenu;

    public MainMenu(UI ui, TeacherMenu teacherMenu,
            CourseMenu courseMenu,
            StudentMenu studentMenu,
            ProgramMenu programMenu) {

        this.ui = ui;
        this.teacherMenu = teacherMenu;
        this.courseMenu = courseMenu;
        this.studentMenu = studentMenu;
        this.programMenu = programMenu;

    }

    public void mainMenu() {
        //        integration.PopulateDatabase.populateDatabase();

        while (true) {
            ui.outputString("-----------------------------------------");
            ui.outputString("School Management System");
            ui.outputString("-----------------------------------------");
            ui.outputString("1. Teacher Menu");
            ui.outputString("2. Course Menu");
            ui.outputString("3. Student Menu");
            ui.outputString("4. Program Menu");

            ui.outputString("0. Exit");
            ui.outputString("-----------------");
            int choice = ui.takeChoiceInput(4);
            ui.clear();
            switch (choice) {
                case 1:
                    teacherMenu.teacherMenu();
                    break;
                case 2:
                    courseMenu.courseMenu();
                    break;
                case 3:
                    studentMenu.studentMenu();
                    break;
                case 4:
                     programMenu.programMenu();
                    break;
                case 0:
                    ui.exit();
            }
            ui.clear();
        }
    }

}
