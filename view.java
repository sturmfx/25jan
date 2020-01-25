
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.router.Route;
import java.io.*;
import java.util.ArrayList;
@Route
@PWA(name = "My Application", shortName = "My Application")
public class MainView extends HorizontalLayout
{
    TextField name = new TextField("NAME");
    TextField sname = new TextField("SECOND NAME");
    TextField age = new TextField("AGE");
    TextField email = new TextField("EMAIL");
    Button add = new Button("ADD", event -> add());

    Button save = new Button("SAVE", event -> save());
    Button load = new Button("LOAD", event -> load());
    NumberField file = new NumberField("FILE NAME");
    Grid<Person> grid = new Grid<>(Person.class);
    VerticalLayout menu = new VerticalLayout();
    VerticalLayout grid_layout = new VerticalLayout();

    ArrayList<Person> list = new ArrayList<>();

    public MainView()
    {
        init();

        menu.add(name);
        menu.add(sname);
        menu.add(age);
        menu.add(email);
        menu.add(add);
        menu.setWidth("300px");

        grid_layout.add(save);
        grid_layout.add(load);
        grid_layout.add(file);
        grid_layout.add(grid);

        add(menu);
        add(grid_layout);
    }

    public void init()
    {
        grid.setItems(list);
        grid.setSelectionMode(Grid.SelectionMode.NONE);

    }

    public void add()
    {
        list.add(new Person(name.getValue(), sname.getValue(), email.getValue(), age.getValue()));
    }


    public void save()
    {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("files\\" + file.getValue().intValue() + ".txt")))
        {
            list.forEach(p ->
            {
                try
                {
                    bw.write(p.getName() + "," + p.getSname() + "," + p.getEmail() + "," + p.getAge() + "\n");
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            });
        }
        catch(IOException ex)
        {

            System.out.println(ex.getMessage());
        }
    }

    public void load()
    {
        try(BufferedReader br = new BufferedReader(new FileReader("files\\" + file.getValue().intValue() + ".txt")))
        {
            String s;
            String[] s1;
            list.clear();
            while((s=br.readLine())!=null)
            {
               s1 = s.split(",");
               list.add(new Person(s1[0], s1[1], s1[2], s1[3]));
            }
            grid.getDataProvider().refreshAll();
        }
        catch (FileNotFoundException e)
        {

        }
        catch (IOException e)
        {

        }
    }
}
