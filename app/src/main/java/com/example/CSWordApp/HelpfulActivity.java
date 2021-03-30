package com.example.CSWordApp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GestureDetectorCompat;

import com.example.CSWordApp.Adapters.MyExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HelpfulActivity extends AppCompatActivity {

    // Declare ExpandableListView
    ExpandableListView expandableListView;

    // Create ExpandableListAdapter
    ExpandableListAdapter expandableListAdapter;

    List<String> parents; // A list of parents (strings)
    Map<String, List<String>> childrenMap; // An object that maps keys to values.
    // A map cannot contain duplicate keys; each key can map to at most one value
    // Map Documentation: https://developer.android.com/reference/java/util/Map.html
    // HashMap Documentation: https://developer.android.com/reference/java/util/HashMap.html
    // Hashtable Documentation: https://developer.android.com/reference/java/util/Hashtable.html


    private GestureDetectorCompat swipeListener;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpful);

        //-------------------------------------
        toolbar = findViewById(R.id.toolbar);
        //toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_32);
        toolbar.setTitle("Поради та Лайфхаки для тебе");
        setSupportActionBar(toolbar);

        Drawable backArrow = ResourcesCompat.getDrawable(this.getResources(), R.drawable.ic_baseline_arrow_back_32, null);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(backArrow);

        swipeListener = new GestureDetectorCompat(this, new SwipeGestureListener(this));
        //-------------------------------------



        // Get the expandablelistview from our XML layout file
        expandableListView = findViewById(R.id.list_view_expandable);

        // Populate our data and provide associations
        fillData();

        /**
         * Instantiate our ExpandableListAdapter, pass through
         *  - Context (this)
         *  - List of Parents (parents)
         *  - Map populated with our values and their associations (childrenMap)
         *  (requires a constructor which accepts these values)
         **/
        expandableListAdapter = new MyExpandableListAdapter(this, parents, childrenMap);

        // Set the value in the ExpandableListView
        expandableListView.setAdapter(expandableListAdapter);

//        /** Set a listener on the child elements - show toast as an example */
//        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                Toast.makeText(HelpfulActivity.this, parents.get(groupPosition) + " : " + childrenMap.get(parents.get(groupPosition)).get(childPosition), Toast.LENGTH_LONG).show();
//                return false;
//            }
//        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return swipeListener.onTouchEvent(event);
    }

    /**
     * Populate our parents and children with values, associate children to parents with HashMap
     */
    public void fillData() {
        parents = new ArrayList<>(); // List of Parent Items
        childrenMap = new HashMap<>(); // HashMap to map keys to values

        // Add parents to the parents list
        parents.add("1. Зрозумійте, що вивчення англійської мови – не ракетобудування");
        parents.add("2. Не дозволяйте граматиці відлякувати вас");
        parents.add("3. Пам’ятайте про те, що для того, щоб вивчити англійську швидко, не обов’яково платити");
        parents.add("4. Складіть конкретний план");
        parents.add("5. Не тримайтеся лише за одну книгу");
        parents.add("6. Використовуйте спеціальні картки");
        parents.add("7. Складайте свої власні приклади");
        parents.add("8. Отримайте допомогу");
        parents.add("9. Відвідуйте безкоштовні мовні клуби");
        parents.add("10. Старт, головне почни! ");
        parents.add("11. Оточи себе англійською. ");
        parents.add("12. Слухай англійською. ");
        parents.add("13. Дивись англійською.");
        parents.add("14. Говори англійською. ");
        parents.add("15. Пиши англійською. ");
        parents.add("16. Читай англійською. ");

        // Create lists to hold the data for the children of the parents
        List<String> l1 = new ArrayList<>();
        List<String> l2 = new ArrayList<>();
        List<String> l3 = new ArrayList<>();
        List<String> l4 = new ArrayList<>();
        List<String> l5 = new ArrayList<>();
        List<String> l6 = new ArrayList<>();
        List<String> l7 = new ArrayList<>();
        List<String> l8 = new ArrayList<>();
        List<String> l9 = new ArrayList<>();
        List<String> l10 = new ArrayList<>();
        List<String> l11 = new ArrayList<>();
        List<String> l12 = new ArrayList<>();
        List<String> l13 = new ArrayList<>();
        List<String> l14 = new ArrayList<>();
        List<String> l15 = new ArrayList<>();
        List<String> l16 = new ArrayList<>();

        // Add children to the children lists
        l1.add("Навчальний процес є невід’ємною частиною нашого життя. І якщо прикласти достатньо зусиль, ми будемо здатні зробити набагато більше, ніж зазвичай. Не зважайте на свій вік, стать, професію і життєві погляди, адже саме ці чинники можуть не дозволити вам приєднатися до 500-мільйонної «компанії» людей, котрі володіють англійською мовою як рідною. Вам варто тільки дізнатися про можливості, які перед вами відкриються після остаточного вивчення англійської, і усі ваші побоювання і хвилювання миттєво зникнуть. Отже, залишайтеся у спокої та наполегливо працюйте.");
        l2.add("Багато людей скаржаться на те, що вони припинили вивчати англійську, тому що не змогли упоратися з 12 часами чи з будь-якими іншими труднощами. Однак такі страшні граматичні прийоми неодмінно знадобляться вам. Вони ніщо інше, як спеціальні інструменти, котрі збагатять ваше мовлення. Ви колись цікавилися, як працює бензопила, чи не так? Зазвичай вона використовується для обрубування дерев. Граматика працює за подібним принципом. Вам не потрібно розуміти її досконало і запам’ятовувати назви усіх правил для того, щоб правильно їх використовувати.");
        l3.add("Звичайно, послуги учителя або репетитора хороші речі, але якщо так трапилося, що у вас немає на них коштів, ви все одно можете скористатися іншими варіантами. Наприклад, читання безкоштовних електронних книг або перегляд онлайн уроків допоможуть вам знати англійську навіть краще за тих людей, котрі відвідують групові заняття.");
        l4.add("Ставтеся до вивчення англійської мови настільки серйозно, немов це ваш особистий бізнес-проект. Кожного дня приділяйте хоча б 30 хвилин читанню, аудіюванню чи вивченню слів. Встановіть для себе ціль, яку ви повинні будете досягнути у читанні: наприклад, 300 сторінок на місяць. Дотримуйтеся цієї кількості з ювелірною точністю. Читайте хоча б 2 журналістські статті на тиждень. Використовуйте такі сервіси, як «Words» від «BBC» для збагачування своєї лексики. Відстежуйте свій прогрес і переглядайте уже прочитаний матеріал декілька разів. Проводьте самодіагностику за допомогою онлайн-тестів.");
        l5.add("У магазинах можна зустріти безліч навчальних книжок, однак ні одна з них не є ідеальною. Проглядайте різні тексти та теми. Вибирайте тільки той матеріал, котрий вас цікавить. Якщо у одній книзі ви знайшли цікаву для вас тему, але з нудним поясненням, шукайте кращі або альтернативні варіанти, але ніколи не здавайтеся у навчанні. Адже ваша майбутня кар’єра залежить від того, які знання ви здобудете сьогодні.");
        l6.add("Простий інструмент – flashcards, двосторонні картки зі словом або словосполученням на одній стороні і з перекладом або поясненням на другій – допоможуть вам запам’ятати слова швидше. Це легкий, але ефективний спосіб вивчення, тому що ви можете користуватися ним будь-де. Якщо вас дратують клаптики паперу у вашій сумці, ви можете користуватися ними у електронному вигляді у додатках для «iOS» та «Android». Деякі з них навіть пропонують різноманітні ігри і кросворди, щоб закласти словник до вашої пам’яті. Коли ви користуєтеся картками, не забувайте про прийменники – без них слова можуть означати зовсім інші речі.");
        l7.add("Слова дуже важливо знати, але вони даремні, якщо ви не будете використовувати їх у своєму мовленні. Не чекайте поки ви не отримаєте можливість використовувати юридичні терміни у коротенькій розмові з незнайомцем. Приділіть невелику кількість часу для написання речень зі словами, котрі ви вже знаєте. Це допоможе вам не тільки добре запам’ятати правопис , а ще й швидко і вільно розмовляти англійською у майбутньому.");
        l8.add("Кожне завдання стає легшим, якщо ви намагаєтеся виконати його разом з кимось. Знайдіть собі компанію або навіть друга, якщо ви бажаєте. Це може бути як родич, так і колега з бажанням вивчати англійську мову. Підбадьорюйте один одного, контролюйте та не дозволяйте своєму приятелю здаватися. Ви навіть можете розігрувати діалоги або невеличкі сценки. Зробіть англійську мову частиною вашого дозвілля, спілкуйтеся нею будь-де і будь-коли. Ви побачити результат такої співпраці. До речі, це може стати початком дружніх стосунків.");
        l9.add("У великих містах є групи людей, котрі збираються декілька разів на місяць, аби покращити мовні навички чи обговорити певну книгу. Такі клуби не можуть бути корисними для новачків у англійській мові, але для більш досвідчених знавців це прекрасно, особливо коли вони не мають іншої можливості поспілкуватися англійською. Якщо у вашому місті немає такого клубу, започаткуйте його – це весело і корисно.");

        l10.add("Змушуй себе, задобрюй цукерками, але сідай і вчи англійську, і ти побачиш, що це не якесь там нудне домашнє завдання, а супер-цікаве проводження часу. А ще головніше don’t give up (не здаватися), тому що у вивченні, неодмінно, найважливіше це частота занять, а не їх тривалість. Тому приділяй англійській, хоча б, по 15 хвилин кожен день.");
        l11.add("Як? На що ти дивишся найчастіше всього? У сучасному світі відповідь очевидна – ТЕЛЕФОН! Тож:\n" +
                "1) Крок перший: змінюємо інтерфейс телефону на англійську; \n" +
                "2) Крок другий: шукаємо цікаві apps для вивчення англійської, чи то для вивчення vocabulary, чи то подкасти типу BBC listening і т.п.;\n" +
                "3) Крок третій: оточуємо себе англійською всюди. Почнемо з дому:) \n" +
                "Як, запам’ятовуй: клеїмо стікери англійських назв на ВСЕ, що бачимо, головне домашніх улюбленців не чіпайте, jk (just kidding). І от уже ти ходиш по flat, відкриваєш fridge, береш там juice, і відчуваєш себе справжнім Englishman. ");
        l12.add("А яка твоя улюблена пісня? Ага, вона англійською мовою? Ну тож чудово, наступний раз, як будеш їхати в маршруточці і слухати свій плейліст, згадай просту математичну формулу: \n" +
                "гуглиш слова з перекладом пісень + читаєш їх + підспівуєш + вчиш lyrics = плюс десяток, а то і сотень нових слів англійською в словниковий запас \n" +
                "ГА-РАН-ТО-ВА-НО! \"");
        l13.add("Так-так, це про фільми, серіали та, звичайно, відосики на ютуб. Якщо лише почав вчити англійську, порада: почни з серіалу Extra, а далі втягнешся і перейдеш на щось складніше. Для тих, хто вже не новачок в English, радимо TED talks та різні популярні серіали типу Gossip Girl, The Vampire Diaries, Riverdale. Плюс в тому, що з англійською будеш завжди в темі, адже серіальчики виходять в оригіналі раніше, ніж з перекладом, тож будеш всім спойлерити наступні серії :)");
        l14.add("Не біда якщо в тебе немає друга нейтів спікера, є й інші способи. Один з них, говори зі своїми знайомими, друзями, близькими. Це крутий лайфхак для секретиків. \n" +
                " \n" +
                "А-ну ось така в нас ситуація:\n" +
                "Уяви, ви з подругою/другом обговорюєте ваше особисте життя, а тут поряд іде ваш знайомий! Що ж робити? Не повіриш, але вчити англійську, та ще й закликати своїх друзів та рідних. Та все ж, до чого це?\n" +
                "Дубль номер 2. Ви з подругою/другом обговорюєте ваше особисте життя, а тут поряд іде ваш знайомий! Що ж робити? Та нічого, адже ви share secrets with each other (обмінюєтеся секретиками однин з одним) in English. And unlikely that someone understands what you are talking about (і навряд чи хтось зрозуміє про що йде ваша розмова). Навіть якщо ваш знайомий вчить англійську, йому складно буде відразу схопити про що ж йде мова, адже це не його native language.\n" +
                "Тож спробуй знайти собі свого buddy learner (друга по вивченню мови), і уявляйте себе справжніми іноземцями.\n" +
                "Інший крутий та головне найдієвіший спосіб – це, of course, курси, speaking clubs, тут ти точно зможеш на 100% прокачати свій англійський у всіх аспектах. Тадааам Green Country може стати чарівником і підтримати тебе у цьому починанні.");
        l15.add("Що саме? Та будь-що: свої думки, шкільний щоденник (розклад уроків), планнер, нотатки, перекладай свої твори з української на англійську. Та взагалі, згадай часи «любого щоденника» і заміни його новеньким – “dear diary”. І все! Навіть не треба ніякого ключику, щоб ніхто не зміг прочитати твої секретики.");
        l16.add("Починаємо з англомовних блогів, закінчуємо книжечками.");

        // Associate children with parents using the childrenMap HashMap
        // Associate susanChildren with "Parent - Susan"
        childrenMap.put(parents.get(0),l1);
        childrenMap.put(parents.get(1),l2);
        childrenMap.put(parents.get(2),l3);
        childrenMap.put(parents.get(3),l4);
        childrenMap.put(parents.get(4),l5);
        childrenMap.put(parents.get(5),l6);
        childrenMap.put(parents.get(6),l7);
        childrenMap.put(parents.get(7),l8);
        childrenMap.put(parents.get(8),l9);
        childrenMap.put(parents.get(9),l10);
        childrenMap.put(parents.get(10),l11);
        childrenMap.put(parents.get(11),l12);
        childrenMap.put(parents.get(12),l13);
        childrenMap.put(parents.get(13),l14);
        childrenMap.put(parents.get(14),l15);
        childrenMap.put(parents.get(15),l16);


    }
}