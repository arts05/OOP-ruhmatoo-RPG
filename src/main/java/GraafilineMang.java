import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GraafilineMang extends Application {
    private Stage stage;

    private Kangelane kangelane;
    private Lohe lohe;
    private LoheTegevus järgmineTegevus;

    private Label kangelaseHpLabel;
    private Label loheHpLabel;
    private Label pildiPlaceholder;
    private Label tekstiKast;
    private VBox valikuteKast;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setTitle("Printsessi päästmine");
        näitaPeamenüü();
    }

    /**
     * Kuvab sissejuahtuse ekraani peale peamenüüd.
     **/
    private void näitaIntro() {
        StackPane root = new StackPane();

        // Loome taustapildi kasutades loss.png resources kaustast.
        ImageView taustaPilt = new ImageView(
                new Image(getClass().getResource("/loss.png").toExternalForm())
        );

        // Pilt venitatakse akna suuruseks.
        taustaPilt.setPreserveRatio(false);
        taustaPilt.fitWidthProperty().bind(root.widthProperty());
        taustaPilt.fitHeightProperty().bind(root.heightProperty());

        root.getChildren().add(taustaPilt);

        // Intro tekstipaneel, mis on poolläbipaistev.
        VBox introPaneel = new VBox(20);
        introPaneel.setAlignment(Pos.CENTER);
        introPaneel.setMaxWidth(720);
        introPaneel.setMaxHeight(420);
        introPaneel.setPadding(new Insets(35));

        introPaneel.setStyle(
                "-fx-background-color: rgba(10, 10, 25, 0.75);" +
                        "-fx-border-color: rgba(255, 255, 255, 0.85);" +
                        "-fx-border-width: 3;" +
                        "-fx-background-radius: 8;" +
                        "-fx-border-radius: 8;"
        );

        // Intro pealkiri.
        Label pealkiri = new Label("Sinu teekond algab");
        pealkiri.setTextFill(Color.WHITE);
        pealkiri.setFont(Font.font(28));

        // Intro põhitekst.
        Label introTekst = new Label(
                "Sa oled üks vapper sõdalane, kelle abi on siinsete maade kuningas palunud, ning seda ühel\n" +
                        "eesmärgil: päästa printsess hirmsa lohe käest. Rännates läbi mägede, oled peagi kohal ning\n" +
                        "valmis temaga silmitsi seisma...\n\n" +
                        "Kes sa täpsemalt oled? Oled sa täpse silma ja käega või oled sa suuteline oma löökidega\n" +
                        "igat vastast vappuma panema? Tee oma valik:\n\n" +
                        "Mõõgamees. Annad matse ja talud neid rohkem.\n" +
                        "Vibumees. Suurem täpsus lendava lohe vastu, sul tasub vahet hoida."
        );

        introTekst.setTextFill(Color.WHITE);
        introTekst.setFont(Font.font(17));
        introTekst.setWrapText(true);
        introTekst.setAlignment(Pos.CENTER);

        // Nupud intro ekraanil, et edasi ja tagasi liikuda.
        Button edasi = new Button("Edasi");
        Button tagasi = new Button("Tagasi");

        edasi.setPrefWidth(180);
        tagasi.setPrefWidth(180);

        // Viib edasi klassi valimise juurde.
        edasi.setOnAction(e -> näitaKlassiValik());

        // Viib tagasi peamenüü juurde.
        tagasi.setOnAction(e -> näitaPeamenüü());

        // Nuppude paigutus.
        HBox nupud = new HBox(15);
        nupud.setAlignment(Pos.CENTER);
        nupud.getChildren().addAll(tagasi, edasi);

        introPaneel.getChildren().addAll(pealkiri, introTekst, nupud);

        root.getChildren().add(introPaneel);

        stage.setScene(new Scene(root, 900, 600));
    }

    /**
     * Loob mängu peamenüü. Saab valida kas väljuda või alustada mängu.
     */
    private void näitaPeamenüü() {
        StackPane root = new StackPane();

        // Taustapilt.
        ImageView taustaPilt = new ImageView(
                new Image(getClass().getResource("/loss.png").toExternalForm())
        );

        taustaPilt.setPreserveRatio(false);
        taustaPilt.fitWidthProperty().bind(root.widthProperty());
        taustaPilt.fitHeightProperty().bind(root.heightProperty());

        root.getChildren().add(taustaPilt);

        // Menüü keskne paneel.
        VBox menüüPaneel = new VBox(25);
        menüüPaneel.setAlignment(Pos.CENTER);
        menüüPaneel.setMaxWidth(420);
        menüüPaneel.setMaxHeight(250);
        menüüPaneel.setPadding(new Insets(35));

        // Poolläbipaistev taust.
        menüüPaneel.setStyle(
                "-fx-background-color: rgba(10, 10, 25, 0.68);" +
                        "-fx-background-radius: 8;" +
                        "-fx-border-radius: 8;"
        );

        Label pealkiri = new Label("PRINTSESSI PÄÄSTMINE");
        pealkiri.setTextFill(Color.WHITE);
        pealkiri.setFont(Font.font(30));

        Button mängi = new Button("Mängi");
        Button välju = new Button("Välju");

        mängi.setPrefWidth(180);
        välju.setPrefWidth(180);

        // Mäng viib intro ekraani juurde
        mängi.setOnAction(e -> näitaIntro());

        // Mäng paneb ennast kinni.
        välju.setOnAction(e -> stage.close());

        menüüPaneel.getChildren().addAll(pealkiri, mängi, välju);
        root.getChildren().add(menüüPaneel);

        Scene scene = new Scene(root, 900, 600);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Loob ekraani, kus mängija saab valida tegelasklassi.
     */
    private void näitaKlassiValik() {
        StackPane root = new StackPane();

        ImageView taustaPilt = new ImageView(
                new Image(getClass().getResource("/loss.png").toExternalForm())
        );

        taustaPilt.setPreserveRatio(false);
        taustaPilt.fitWidthProperty().bind(root.widthProperty());
        taustaPilt.fitHeightProperty().bind(root.heightProperty());

        root.getChildren().add(taustaPilt);

        // Klassivaliku paneel.
        VBox valikuPaneel = new VBox(20);
        valikuPaneel.setAlignment(Pos.CENTER);
        valikuPaneel.setMaxWidth(500);
        valikuPaneel.setMaxHeight(300);
        valikuPaneel.setPadding(new Insets(35));

        valikuPaneel.setStyle(
                "-fx-background-color: rgba(10, 10, 25, 0.75);" +
                        "-fx-border-color: rgba(255, 255, 255, 0.85);" +
                        "-fx-border-width: 3;" +
                        "-fx-background-radius: 8;" +
                        "-fx-border-radius: 8;"
        );

        Label tekst = new Label("Vali kangelane");
        tekst.setTextFill(Color.WHITE);
        tekst.setFont(Font.font(26));

        Button warrior = new Button("Mõõgamees");
        Button archer = new Button("Vibumees");
        Button tagasi = new Button("Tagasi");

        warrior.setPrefWidth(220);
        archer.setPrefWidth(220);
        tagasi.setPrefWidth(220);

        // Kui valitakse mõõgamees, alustatakse mängu temana ja luuakse Warrior objekt.
        warrior.setOnAction(e -> alustaMäng(new Warrior()));
        // Kui valitakse vibumees, alustatakse mängu temana ja luuakse Archer objekt.
        archer.setOnAction(e -> alustaMäng(new Archer()));
        // Tagasi viib intro ekraanile.
        tagasi.setOnAction(e -> näitaIntro());

        valikuPaneel.getChildren().addAll(tekst, warrior, archer, tagasi);

        root.getChildren().add(valikuPaneel);

        stage.setScene(new Scene(root, 900, 600));
    }

    /**
     * Käivitab mängu valitud kangelasega.
     */
    private void alustaMäng(Kangelane valitudKangelane) {
        kangelane = valitudKangelane;
        lohe = new Lohe();

        // Lohe valib esimese tegevuse.
        // Sellet egevuse vihjet näidatakse mängijale enne tema otsust.
        järgmineTegevus = lohe.valiTegevus();

        // Loome mängu põhivaate.
        looMänguVaade();

        // Algne tekst ja placeholder.
        kirjutaTekst("Oled jõudnud lohe kantsi. Lohe valmistub ründama.\nVihje: " + järgmineTegevus.getVihje());
        uuendaVaade("LOHE OOTAB");

        // Kuvame mängija põhivalikud.
        näitaPõhiValikud();
    }

    /**
     * Loob põhilise mänguvaate.
     * Üleval on HP-d, keskel on placeholder, all tekstikast ja valikunupud.
     */
    private void looMänguVaade() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #202020;");

        // Ülemine riba on HP näitude jaoks.
        HBox ülemineRiba = new HBox();
        ülemineRiba.setPadding(new Insets(12));
        ülemineRiba.setAlignment(Pos.CENTER);
        ülemineRiba.setSpacing(250);

        kangelaseHpLabel = new Label();
        loheHpLabel = new Label();

        kangelaseHpLabel.setTextFill(Color.WHITE);
        loheHpLabel.setTextFill(Color.WHITE);
        kangelaseHpLabel.setFont(Font.font(18));
        loheHpLabel.setFont(Font.font(18));

        ülemineRiba.getChildren().addAll(kangelaseHpLabel, loheHpLabel);

        // Keskel olev placeholer. HILJEM ASENDADA PILDIGA.
        pildiPlaceholder = new Label("PLACEHOLDER");
        pildiPlaceholder.setAlignment(Pos.CENTER);
        pildiPlaceholder.setTextFill(Color.WHITE);
        pildiPlaceholder.setFont(Font.font(36));
        pildiPlaceholder.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        pildiPlaceholder.setStyle("-fx-background-color: #3a3a3a; -fx-border-color: white; -fx-border-width: 3;");

        StackPane keskmine = new StackPane(pildiPlaceholder);
        keskmine.setPadding(new Insets(10));

        // Alumine osa sisaldab tekstiklassi ja valikute kasti.
        HBox alumine = new HBox(10);
        alumine.setPadding(new Insets(10));
        alumine.setPrefHeight(190);

        // Tekstikast mängude sündmuste kuvamiseks.
        tekstiKast = new Label();
        tekstiKast.setWrapText(true);
        tekstiKast.setTextFill(Color.WHITE);
        tekstiKast.setFont(Font.font(17));
        tekstiKast.setPadding(new Insets(15));
        tekstiKast.setAlignment(Pos.TOP_LEFT);
        tekstiKast.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        tekstiKast.setStyle("-fx-background-color: #101020; -fx-border-color: white; -fx-border-width: 3;");

        // Valikute kast.
        valikuteKast = new VBox(8);
        valikuteKast.setPadding(new Insets(10));
        valikuteKast.setPrefWidth(220);
        valikuteKast.setStyle("-fx-background-color: #101020; -fx-border-color: white; -fx-border-width: 3;");

        // Tekstikast võtab ülejäänud laiuse.
        HBox.setHgrow(tekstiKast, Priority.ALWAYS);

        alumine.getChildren().addAll(tekstiKast, valikuteKast);

        root.setTop(ülemineRiba);
        root.setCenter(keskmine);
        root.setBottom(alumine);

        stage.setScene(new Scene(root, 900, 600));
        uuendaHp();
    }

    /**
     * Kuvab mängija põhivalikud:
     * 1) Ründa
     * 2) Peida
     * 3) Välju (völjuda mängust)
     */
    private void näitaPõhiValikud() {
        valikuteKast.getChildren().clear();

        Button ründa = new Button("Ründa");
        Button peida = new Button("Peida");
        Button välju = new Button("Välju");

        seadistaNupp(ründa);
        seadistaNupp(peida);
        seadistaNupp(välju);

        // Ründa avab kehaosade valiku.
        ründa.setOnAction(e -> näitaSihtmärgid());
        // Peida paneb kangelase peitu.
        peida.setOnAction(e -> peidaKangelane());
        // Välju väljub mängust.
        välju.setOnAction(e -> stage.close());

        valikuteKast.getChildren().addAll(ründa, peida, välju);
    }

    /**
     * Kuvab nupud kõigi lohe kehaosade jaoks
     * Mängija saab valida, kuhu rünnata.
     */
    private void näitaSihtmärgid() {
        valikuteKast.getChildren().clear();

        lisaSihtmärgiNupp("Pea", lohe.getPea());
        lisaSihtmärgiNupp("Kere", lohe.getTorso());
        lisaSihtmärgiNupp("Vasak tiib", lohe.getVasakTiib());
        lisaSihtmärgiNupp("Parem tiib", lohe.getParemTiib());
        lisaSihtmärgiNupp("Vasak jalg", lohe.getVasakJalg());
        lisaSihtmärgiNupp("Parem jalg", lohe.getParemJalg());

        Button tagasi = new Button("Tagasi");
        seadistaNupp(tagasi);
        // Tagasi viib põhivalikute juurde.
        tagasi.setOnAction(e -> näitaPõhiValikud());

        valikuteKast.getChildren().add(tagasi);
    }

    /**
     * Abimeetod, mis loob ühe sihtmärgi nupu. Nupule kuvatakse kehaosa nimi ja preageune HP.
     */
    private void lisaSihtmärgiNupp(String nimi, KehaOsa kehaOsa) {
        Button nupp = new Button(nimi + " (" + kehaOsa.getHp() + " HP)");
        seadistaNupp(nupp);
        // Vajutamisel ründab mängija seda kehaosa.
        nupp.setOnAction(e -> mängijaRündab(kehaOsa));
        valikuteKast.getChildren().add(nupp);
    }

    /**
     * Lahendab mängija rünnaku valitud kehaosa vastu.
     */
    private void mängijaRündab(KehaOsa sihtmärk) {
        // Kui kehaosa hävitatud, siis ei saa seda uuesti rünnata.
        if (sihtmärk.hävitatud()) {
            kirjutaTekst("See kehaosa on juba hävitatud. Vali teine sihtmärk.");
            return;
        }
        // Rünnak valitakse tegelase klassi põhjal (Warrior = mõõl, Archer = vibu)
        Rünnak rünnak = (kangelane instanceof Warrior) ? Rünnak.mõõk() : Rünnak.vibu();

        // Tabamustõenäosuse arvutus.
        int tabamus = kangelane.tabamusTõenäosus(rünnak, lohe, sihtmärk);
        // Veeretame %
        int vise = Täring.veeretaProtsent();

        // Kui vise jääb tabamustõenäosuse sisse, siis rünnak tabab.
        if (vise <= tabamus) {
            // Kahju tegemine lohele.
            int damage = kangelane.teeHaiget(rünnak, lohe);
            sihtmärk.saaHaiget(damage);
            lohe.suurendaViha(2);

            // Muudame placeholder pilti.
            uuendaVaade("KANGELANE RÜNDAB");

            String tekst = "Rünnak tabas.\nTegid " + sihtmärk.getNimiOmastavas() + " pihta " + damage + " kahju.\n";
            tekst += "Tabamustõenäosus: " + tabamus + "%, vise: " + vise + ".";

            // Kui on jalg hävitatud, siis lohe kohe läheb lendu.
            if ((sihtmärk == lohe.getVasakJalg() || sihtmärk == lohe.getParemJalg()) && sihtmärk.hävitatud()) {
                lohe.setSunniLeegigaRünnak(true);
                tekst += "\nLohe jalg hävis. Lohe vihastab ja valmistub leeki heitma.";
            }

            // Kui hävitatud kehaosa on tiib, siis lohe ei saa enam lennata.
            if ((sihtmärk == lohe.getVasakTiib() || sihtmärk == lohe.getParemTiib()) && sihtmärk.hävitatud()) {
                if (!lohe.saabLennata()) {
                    lohe.setLendab(false);
                    tekst += "\nLohe tiib hävis. Lohe ei saa enam lennata.";
                }
            }

            kirjutaTekst(tekst);
        } else {
            // Kui rünnak ei taba, siis kahju ei tehta.
            uuendaVaade("KANGELANE RÜNDAB");
            kirjutaTekst("Rünnak läks mööda.\nTabamustõenäosus: " + tabamus + "%, vise: " + vise + ".");
        }

        // Uuendame HP pärast rünnakut.
        uuendaHp();

        // Kui lohe suri, lõpetame mängu võiduga.
        if (!lohe.kasElus()) {
            lõpetaMäng(true);
            return;
        }

        // Kui lohe jäi ellu, siis liigume lohe käigu juurde.
        näitaJätkaLoheKäigule();
    }

    /**
     * Paneb kangelase peitu.
     * Peidus olemine vähendab lohe rünnakust saadavat kahju.
     */
    private void peidaKangelane() {
        kangelane.setPeidus(true);
        uuendaVaade("KANGELANE PEIDUS");
        kirjutaTekst(kangelane.getNimi() + " peidab end kivide taha.");

        // Pärast peitmist liigub mäng edasi lohe käigule
        näitaJätkaLoheKäigule();
    }

    /**
     * Kuvab nupu, millega mängija saab kohe liikuda edasi lohe käigule
     * Pärast mängija rünnatkut või peitmist.
     */
    private void näitaJätkaLoheKäigule() {
        valikuteKast.getChildren().clear();

        Button jätka = new Button("Jätka");
        seadistaNupp(jätka);
        jätka.setOnAction(e -> loheRündab());

        valikuteKast.getChildren().add(jätka);
    }

    /**
     * Lahendab lohe rünnaku, mis oli varem salvestatud muutujasse järgmineTegevus
     */
    private void loheRündab() {
        // Arvutada lohe rünnaku algkahju.
        int sissetulevKahju = lohe.veeretaRünnakuDamage(järgmineTegevus);

        // Kui kangelane on peidus, siis vähendada kahju Kaitse klassi abil.
        if (kangelane.isPeidus()) {
            Kaitse kaitse = new Kaitse();
            sissetulevKahju = kaitse.blokeeriKahju(kangelane, sissetulevKahju);
        }

        // Rakendame kahju kangelasele.
        kangelane.saaHaavata(sissetulevKahju);

        // Muuta keskmist placeholderit vastavalt lohe tegevusele.
        if (järgmineTegevus == LoheTegevus.heidaLeeki()) {
            uuendaVaade("LOHE HEIDAB LEEKI");
        } else if (järgmineTegevus == LoheTegevus.ründaSabaga()) {
            uuendaVaade("LOHE RÜNDAB SABAGA");
        } else if (järgmineTegevus == LoheTegevus.ründaJalaga()) {
            uuendaVaade("LOHE RÜNDAB JALAGA");
        }

        kirjutaTekst("Lohe tegevus: " + järgmineTegevus.getNimi() + ".\nLohe teeb " + sissetulevKahju + " kahju.");

        uuendaHp();

        // Kui kangelane suri, lõppeb mäng kaotusega.
        if (!kangelane.kasElus()) {
            lõpetaMäng(false);
            return;
        }

        // Vooru lõpus eemaldada peidus olek ja vähendada lohe viha.
        kangelane.lõpetaKord();
        lohe.lõpetaKord();

        // Lohe valib järgmise vooru tegevuse.
        järgmineTegevus = lohe.valiTegevus();

        // Kuvada nupp järgmise vooru alustamiseks.
        näitaJärgmineVoor();
    }

    /**
     * Kuvab nupu, millega mängija saab alustata uut vooru.
     */
    private void näitaJärgmineVoor() {
        valikuteKast.getChildren().clear();

        Button jätka = new Button("Järgmine voor");
        seadistaNupp(jätka);

        // Uuendame placeholderi vastavalt sellele, kas lohe lendab või on maas, anname lohe vihje ja näitame valikuid.
        jätka.setOnAction(e -> {
            uuendaVaade(lohe.lendab() ? "LOHE LENDAB" : "LOHE MAAS");
            kirjutaTekst("Vihje: " + järgmineTegevus.getVihje());
            näitaPõhiValikud();
        });

        valikuteKast.getChildren().add(jätka);
    }

    /**
     * Lõpetab mängu.
     * Kui võit = true, siis mängija võitus
     * Kui võit = false, siis mängija kaotas.
     */
    private void lõpetaMäng(boolean võit) {
        valikuteKast.getChildren().clear();

        if (võit) {
            uuendaVaade("LOHE ALISTATUD");
            kirjutaTekst("Palju õnne. Alistasid lohe ja printsess on päästetud.");
        } else {
            uuendaVaade("KANGELANE LANGES");
            kirjutaTekst("Lohe osutus liiga tugevaks. Sinu teekond lõppes siin.");
        }

        Button menüü = new Button("Peamenüü");
        seadistaNupp(menüü);
        // Peamenüü alustab kasutaja jaoks uue mängustükli.
        menüü.setOnAction(e -> näitaPeamenüü());

        Button välju = new Button("Välju");
        seadistaNupp(välju);
        välju.setOnAction(e -> stage.close());

        valikuteKast.getChildren().addAll(menüü, välju);
    }

    /**
     * Uuendab ülemise rea HP-d.
     */
    private void uuendaHp() {
        kangelaseHpLabel.setText(kangelane.getNimi() + " HP: " + kangelane.getHp() + "/" + kangelane.getMaxHp());

        int loheHpKokku =
                lohe.getPea().getHp()
                        + lohe.getTorso().getHp()
                        + lohe.getVasakTiib().getHp()
                        + lohe.getParemTiib().getHp()
                        + lohe.getVasakJalg().getHp()
                        + lohe.getParemJalg().getHp();

        loheHpLabel.setText("Lohe HP kokku: " + loheHpKokku);
    }

    /**
     * Muudab placeholderi teksti.
     */
    private void uuendaVaade(String tekst) {
        pildiPlaceholder.setText(tekst);
    }

    /**
     * Kirjutab alumisse tekstikasti uue teksti.
     */
    private void kirjutaTekst(String tekst) {
        tekstiKast.setText(tekst);
    }

    /**
     * Ühtlustab kõik mänguvalikute nupud.
     */
    private void seadistaNupp(Button nupp) {
        nupp.setMaxWidth(Double.MAX_VALUE);
        nupp.setPrefHeight(35);
    }
}