package pages.directories.courses;

import com.codeborne.selenide.SelenideElement;
import model.Courses;
import pages.elements.BasePage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class CreateCoursesPage extends BasePage {
    public SelenideElement nameArea = $("input[name=\"Name\"]");
    public SelenideElement codCurse = $("input[name=\"Code\"]");
    public SelenideElement duration = $("#Duration");
    private SelenideElement wrapDuration = $("#control-group-Duration .k-numerictextbox");
    public SelenideElement programOfCurse = $("textarea[name=\"CourseProgram\"]");
    public SelenideElement weight = $("#Weight");
    private SelenideElement wrapWeight = $("#control-group-Weight .k-numerictextbox");
    public SelenideElement editor = $("select[name=\"Editors\"]");
    public SelenideElement descriptionArea = $("textarea[name=\"Description\"]");




    public void fillFields(Courses courses) {
        sleep(3000);
        typeOrSkip(nameArea, courses.getName());
        typeOrSkip(codCurse, courses.getCourseCode());
        typeOrSkipNumeric(wrapDuration, duration, courses.getDuration());
        typeOrSkip(programOfCurse, courses.getCourseProgram());
        typeOrSkipNumeric(wrapWeight, weight, courses.getWeight());
        typeOrSkip(editor, courses.getEditors());
        typeOrSkip(descriptionArea, courses.getDescription());
    }
}
