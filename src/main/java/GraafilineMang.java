import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.scene.input.KeyEvent;

import java.time.LocalDateTime;

public class GraafilineMang extends Application {
    private Stage stage;

    private Kangelane kangelane;
    private Lohe lohe;
    private LoheTegevus järgmineTegevus;

    private Label kangelaseHpLabel;
    private Label loheHpLabel;
    private ImageView kangelaneSprait;
    private ImageView loheSprait;
    private Label tekstiKast;
    private VBox valikuteKast;

    // [0] passiivne, [1] mõõkarünnak, [2] viburünnak
    private final Image[] kangelaneSpraidid = new Image[3];
    // [0] passiivne, [1] leek, [2] saba, [3] jalg
    private final Image[] loheSpraidid = new Image[4];

    private LocalDateTime mänguAlguseAeg;
    private int voorudeArv;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setTitle("Printsessi päästmine");
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        laadiSpraidid();
        näitaPeamenüü();
    }

    /**
     * Laadib kõik spraidifailid mällu.
     * Kui fail puudub, jäetakse vastav koht null-iks ja animatsioon jäetakse lihtsalt vahele.
     */
    private void laadiSpraidid() {
        String[] kangelaneFailed = {"/warrior1.png", "/warrior2.png", "/warrior3.png"};
        String[] loheFailed    = {"/lohe1.png", "/lohe2.png", "/lohe3.png", "/lohe4.png"};

        for (int i = 0; i < kangelaneFailed.length; i++) {
            try {
                kangelaneSpraidid[i] = new Image(getClass().getResource(kangelaneFailed[i]).toExternalForm(),
                        260, 260, true, false);
            } catch (Exception e) {
                System.out.println("Sprait puudub: " + kangelaneFailed[i]);
            }
        }
        for (int i = 0; i < loheFailed.length; i++) {
            try {
                loheSpraidid[i] = new Image(getClass().getResource(loheFailed[i]).toExternalForm(),
                        260, 260, true, false);
            } catch (Exception e) {
                System.out.println("Sprait puudub: " + loheFailed[i]);
            }
        }
    }

    /**
     * Kuvab sissejuahtuse ekraani peale peamenüüd.
     **/
    private void näitaIntro() {
        StackPane root = new StackPane();

        ImageView taustaPilt = new ImageView(
                new Image(getClass().getResource("/loss.png").toExternalForm())
        );
        taustaPilt.setPreserveRatio(false);
        taustaPilt.fitWidthProperty().bind(root.widthProperty());
        taustaPilt.fitHeightProperty().bind(root.heightProperty());
        root.getChildren().add(taustaPilt);

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

        Label pealkiri = new Label("Sinu teekond algab");
        pealkiri.setTextFill(Color.WHITE);
        pealkiri.setFont(Font.font(28));

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

        Button edasi  = new Button("(Enter) Edasi");
        Button tagasi = new Button("(Esc) Tagasi");

        edasi.setPrefWidth(180);
        tagasi.setPrefWidth(180);

        edasi.setOnAction(e -> näitaKlassiValik());
        tagasi.setOnAction(e -> näitaPeamenüü());

        HBox nupud = new HBox(15);
        nupud.setAlignment(Pos.CENTER);
        nupud.getChildren().addAll(tagasi, edasi);

        introPaneel.getChildren().addAll(pealkiri, introTekst, nupud);
        root.getChildren().add(introPaneel);
        Scene scene = new Scene(root, 900, 600);

        scene.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                e.consume();
                näitaKlassiValik();
            } else if (e.getCode() == KeyCode.ESCAPE) {
                e.consume();
                näitaPeamenüü();
            }
        });

        stage.setScene(scene);
    }

    /**
     * Loob mängu peamenüü.
     */
    private void näitaPeamenüü() {
        StackPane root = new StackPane();

        ImageView taustaPilt = new ImageView(
                new Image(getClass().getResource("/loss.png").toExternalForm())
        );
        taustaPilt.setPreserveRatio(false);
        taustaPilt.fitWidthProperty().bind(root.widthProperty());
        taustaPilt.fitHeightProperty().bind(root.heightProperty());
        root.getChildren().add(taustaPilt);

        VBox menüüPaneel = new VBox(25);
        menüüPaneel.setAlignment(Pos.CENTER);
        menüüPaneel.setMaxWidth(420);
        menüüPaneel.setMaxHeight(250);
        menüüPaneel.setPadding(new Insets(35));
        menüüPaneel.setStyle(
                "-fx-background-color: rgba(10, 10, 25, 0.68);" +
                "-fx-background-radius: 8;" +
                "-fx-border-radius: 8;"
        );

        Label pealkiri = new Label("PRINTSESSI PÄÄSTMINE");
        pealkiri.setTextFill(Color.WHITE);
        pealkiri.setFont(Font.font(30));

        Button mängi = new Button("(1) Mängi");
        Button välju = new Button("(2) Välju");
        mängi.setPrefWidth(180);
        välju.setPrefWidth(180);

        mängi.setOnAction(e -> näitaIntro());
        välju.setOnAction(e -> stage.close());

        menüüPaneel.getChildren().addAll(pealkiri, mängi, välju);
        root.getChildren().add(menüüPaneel);

        Scene scene = new Scene(root, 900, 600);

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.DIGIT1 || e.getCode() == KeyCode.NUMPAD1 || e.getCode() == KeyCode.ENTER) {
                näitaIntro();
            } else if (e.getCode() == KeyCode.DIGIT2 || e.getCode() == KeyCode.NUMPAD2 || e.getCode() == KeyCode.ESCAPE) {
                stage.close();
            }
        });

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

        Button warrior = new Button("(1) Mõõgamees");
        Button archer  = new Button("(2) Vibumees");
        Button tagasi  = new Button("(3 / Esc) Tagasi");
        warrior.setPrefWidth(220);
        archer.setPrefWidth(220);
        tagasi.setPrefWidth(220);

        warrior.setOnAction(e -> alustaMäng(new Warrior()));
        archer.setOnAction(e -> alustaMäng(new Archer()));
        tagasi.setOnAction(e -> näitaIntro());

        valikuPaneel.getChildren().addAll(tekst, warrior, archer, tagasi);
        root.getChildren().add(valikuPaneel);
        Scene scene = new Scene(root, 900, 600);

        scene.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.DIGIT1) {
                e.consume();
                alustaMäng(new Warrior());
            } else if (e.getCode() == KeyCode.DIGIT2) {
                e.consume();
                alustaMäng(new Archer());
            } else if (e.getCode() == KeyCode.DIGIT3 || e.getCode() == KeyCode.ESCAPE) {
                e.consume();
                näitaIntro();
            }
        });

        stage.setScene(scene);
    }

    /**
     * Käivitab mängu valitud kangelasega.
     */
    private void alustaMäng(Kangelane valitudKangelane) {
        kangelane = valitudKangelane;
        lohe = new Lohe();

        mänguAlguseAeg = LocalDateTime.now();
        voorudeArv = 1;

        järgmineTegevus = lohe.valiTegevus();

        looMänguVaade();

        kirjutaTekst("Oled jõudnud lohe kantsi. Lohe valmistub ründama.\nVihje: " + järgmineTegevus.getVihje());
        näitaPõhiValikud();
    }

    /**
     * Loob põhilise mänguvaate.
     * Üleval HP-ribad, keskel kaks spraiti (kangelane vasakul, lohe paremal), all tekstikast ja valikud.
     */
    private void looMänguVaade() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #202020;");

        // HP riba
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

        // Spraidid
        kangelaneSprait = new ImageView();
        loheSprait = new ImageView();

        kangelaneSprait.setFitHeight(260);
        kangelaneSprait.setPreserveRatio(true);

        loheSprait.setFitHeight(260);
        loheSprait.setPreserveRatio(true);

        if (kangelaneSpraidid[0] != null) {
            kangelaneSprait.setImage(kangelaneSpraidid[0]);
        }

        if (loheSpraidid[0] != null) {
            loheSprait.setImage(loheSpraidid[0]);
        }

        // Välimine keskmine paneel
        StackPane keskmine = new StackPane();
        keskmine.setPadding(new Insets(10));

        // Lahinguala, mille sees on taust ja spraidid
        StackPane lahinguAla = new StackPane();
        lahinguAla.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        lahinguAla.setStyle("-fx-border-color: white; -fx-border-width: 3;");

        // Cave taust
        ImageView lahinguTaust = new ImageView(
                new Image(getClass().getResource("/cave.png").toExternalForm())
        );

        lahinguTaust.setSmooth(false);
        lahinguTaust.setPreserveRatio(false);

        // Väga oluline: pilt ei osale layout'i mõõtmete arvutuses
        lahinguTaust.setManaged(false);

        // Pilt täidab lahinguala
        lahinguTaust.fitWidthProperty().bind(lahinguAla.widthProperty());
        lahinguTaust.fitHeightProperty().bind(lahinguAla.heightProperty());

        // Spraidid tausta peale
        HBox spraitKast = new HBox(80);
        spraitKast.setAlignment(Pos.BOTTOM_CENTER);
        spraitKast.setPadding(new Insets(10, 10, 35, 10));
        spraitKast.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        spraitKast.getChildren().addAll(kangelaneSprait, loheSprait);

        lahinguAla.getChildren().addAll(lahinguTaust, spraitKast);
        keskmine.getChildren().add(lahinguAla);

        // Alumine osa
        HBox alumine = new HBox(10);
        alumine.setPadding(new Insets(10));
        alumine.setPrefHeight(190);

        tekstiKast = new Label();
        tekstiKast.setWrapText(true);
        tekstiKast.setTextFill(Color.WHITE);
        tekstiKast.setFont(Font.font(17));
        tekstiKast.setPadding(new Insets(15));
        tekstiKast.setAlignment(Pos.TOP_LEFT);
        tekstiKast.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        tekstiKast.setStyle("-fx-background-color: #101020; -fx-border-color: white; -fx-border-width: 3;");

        valikuteKast = new VBox(8);
        valikuteKast.setPadding(new Insets(10));
        valikuteKast.setPrefWidth(220);
        valikuteKast.setStyle("-fx-background-color: #101020; -fx-border-color: white; -fx-border-width: 3;");

        HBox.setHgrow(tekstiKast, Priority.ALWAYS);
        alumine.getChildren().addAll(tekstiKast, valikuteKast);

        root.setTop(ülemineRiba);
        root.setCenter(keskmine);
        root.setBottom(alumine);

        Scene scene = new Scene(root, 900, 600);
        lisaKlaviatuurigaJuhtimine(scene);
        stage.setScene(scene);
        uuendaHp();
    }

    // -------------------------------------------------------------------------
    // Animatsioonid
    // -------------------------------------------------------------------------

    /**
     * Näitab rünnakuspraiti ~1,4 sekundit, seejärel läheb passiivse spraidi juurde tagasi.
     * Kui kumbki pilt on null (fail puudus), ei tee midagi.
     */
    private void näitaRünnakuSprait(ImageView sprait, Image ründeSprait, Image passiivneSprait) {
        if (ründeSprait == null || passiivneSprait == null) return;
        sprait.setImage(ründeSprait);
        PauseTransition oota = new PauseTransition(Duration.millis(1400));
        oota.setOnFinished(e -> sprait.setImage(passiivneSprait));
        oota.play();
    }

    /**
     * Raputab spraiti vasakule-paremale kahaneva amplituudiga (Pokémoni-stiilis).
     */
    private void raputaSprait(ImageView sprait) {
        double[] sammud = {14, -11, 8, -5, 3, -1.5, 0};
        Timeline raputus = new Timeline();
        double kestusMs = 0;
        for (double amp : sammud) {
            kestusMs += 60;
            raputus.getKeyFrames().add(new KeyFrame(
                    Duration.millis(kestusMs),
                    new KeyValue(sprait.translateXProperty(), amp, Interpolator.EASE_BOTH)
            ));
        }
        raputus.play();
    }

    // -------------------------------------------------------------------------
    // Mängu põhivalikud
    // -------------------------------------------------------------------------

    /**
     * Kuvab mängija põhivalikud: Ründa / Peida / Välju.
     */
    private void näitaPõhiValikud() {
        valikuteKast.getChildren().clear();

        Button ründa = new Button("(1) Ründa");
        Button peida = new Button("(2) Peida");
        Button välju = new Button("(3 / Esc) Välju");

        seadistaNupp(ründa);
        seadistaNupp(peida);
        seadistaNupp(välju);

        ründa.setOnAction(e -> näitaSihtmärgid());
        peida.setOnAction(e -> peidaKangelane());
        välju.setOnAction(e -> stage.close());

        valikuteKast.getChildren().addAll(ründa, peida, välju);
    }

    /**
     * Kuvab nupud kõigi lohe kehaosade jaoks.
     */
    private void näitaSihtmärgid() {
        valikuteKast.getChildren().clear();

        lisaSihtmärgiNupp("(1) Pea",         lohe.getPea());
        lisaSihtmärgiNupp("(2) Kere",        lohe.getTorso());
        lisaSihtmärgiNupp("(3) Vasak tiib",  lohe.getVasakTiib());
        lisaSihtmärgiNupp("(4) Parem tiib",  lohe.getParemTiib());
        lisaSihtmärgiNupp("(5) Vasak jalg",  lohe.getVasakJalg());
        lisaSihtmärgiNupp("(6) Parem jalg",  lohe.getParemJalg());

        Button tagasi = new Button("(Esc) Tagasi");
        seadistaNupp(tagasi);
        tagasi.setOnAction(e -> näitaPõhiValikud());

        valikuteKast.getChildren().add(tagasi);
    }

    /**
     * Loob ühe sihtmärgi nupu kehaosa nime ja praeguse HP-ga.
     */
    private void lisaSihtmärgiNupp(String nimi, KehaOsa kehaOsa) {
        Button nupp = new Button(nimi + " (" + kehaOsa.getHp() + " HP)");
        seadistaNupp(nupp);
        nupp.setOnAction(e -> mängijaRündab(kehaOsa));
        valikuteKast.getChildren().add(nupp);
    }

    /**
     * Lahendab mängija rünnaku.
     * Kasutab MängijaRünnak objekti, et koondada relvavalik ja sihtmärk ühte kohta.
     */
    private void mängijaRündab(KehaOsa sihtmärk) {
        if (sihtmärk.hävitatud()) {
            kirjutaTekst("See kehaosa on juba hävitatud. Vali teine sihtmärk.");
            return;
        }

        // MängijaRünnak koondab relvavalik + sihtmärk
        Rünnak rünnak = (kangelane instanceof Warrior) ? Rünnak.mõõk() : Rünnak.vibu();
        MängijaRünnak mRünnak = new MängijaRünnak(rünnak, sihtmärk);

        // Warrior kasutab warrior2, Archer kasutab warrior3
        Image ründeSprait = (mRünnak.getRünnak() == Rünnak.mõõk())
                ? kangelaneSpraidid[1]
                : kangelaneSpraidid[2];
        näitaRünnakuSprait(kangelaneSprait, ründeSprait, kangelaneSpraidid[0]);

        int tabamus = kangelane.tabamusTõenäosus(mRünnak.getRünnak(), lohe, mRünnak.getSihtmärk());
        int vise    = Täring.veeretaProtsent();

        if (vise <= tabamus) {
            int damage = kangelane.teeHaiget(mRünnak.getRünnak(), lohe);
            mRünnak.getSihtmärk().saaHaiget(damage);
            lohe.suurendaViha(2);

            // Lohe saab pihta → raputab
            raputaSprait(loheSprait);

            String tekst = "Rünnak tabas.\nTegid " + mRünnak.getSihtmärk().getNimiOmastavas()
                    + " pihta " + damage + " kahju.\n"
                    + "Tabamustõenäosus: " + tabamus + "%, vise: " + vise + ".";

            if ((mRünnak.getSihtmärk() == lohe.getVasakJalg()
                    || mRünnak.getSihtmärk() == lohe.getParemJalg())
                    && mRünnak.getSihtmärk().hävitatud()) {
                lohe.setSunniLeegigaRünnak(true);
                tekst += "\nLohe jalg hävis. Lohe vihastab ja valmistub leeki heitma.";
            }

            if ((mRünnak.getSihtmärk() == lohe.getVasakTiib()
                    || mRünnak.getSihtmärk() == lohe.getParemTiib())
                    && mRünnak.getSihtmärk().hävitatud()) {
                if (!lohe.saabLennata()) {
                    lohe.setLendab(false);
                    tekst += "\nLohe tiib hävis. Lohe ei saa enam lennata.";
                }
            }

            kirjutaTekst(tekst);
        } else {
            kirjutaTekst("Rünnak läks mööda.\nTabamustõenäosus: " + tabamus + "%, vise: " + vise + ".");
        }

        uuendaHp();

        if (!lohe.kasElus()) {
            lõpetaMäng(true);
            return;
        }

        näitaJätkaLoheKäigule();
    }

    /**
     * Paneb kangelase peitu.
     */
    private void peidaKangelane() {
        kangelane.setPeidus(true);
        kirjutaTekst(kangelane.getNimi() + " peidab end kivide taha.");
        näitaJätkaLoheKäigule();
    }

    /**
     * Kuvab "Jätka" nupu lohe käigu alustamiseks.
     */
    private void näitaJätkaLoheKäigule() {
        valikuteKast.getChildren().clear();

        Button jätka = new Button("(Enter) Jätka");
        seadistaNupp(jätka);
        jätka.setOnAction(e -> loheRündab());

        valikuteKast.getChildren().add(jätka);
    }

    /**
     * Lahendab lohe rünnaku (tegevus oli juba eelmises voorus valitud ja salvestatud).
     */
    private void loheRündab() {
        int sissetulevKahju = lohe.veeretaRünnakuDamage(järgmineTegevus);

        if (kangelane.isPeidus()) {
            Kaitse kaitse = new Kaitse();
            sissetulevKahju = kaitse.blokeeriKahju(kangelane, sissetulevKahju);
        }

        kangelane.saaHaavata(sissetulevKahju);

        // Vali lohe ründessprait tegevuse järgi
        Image loheRündeSprait = null;
        if      (järgmineTegevus == LoheTegevus.heidaLeeki())   loheRündeSprait = loheSpraidid[1];
        else if (järgmineTegevus == LoheTegevus.ründaSabaga())   loheRündeSprait = loheSpraidid[2];
        else if (järgmineTegevus == LoheTegevus.ründaJalaga())   loheRündeSprait = loheSpraidid[3];

        näitaRünnakuSprait(loheSprait, loheRündeSprait, loheSpraidid[0]);

        // Kangelane saab pihta → raputab
        raputaSprait(kangelaneSprait);

        kirjutaTekst("Lohe tegevus: " + järgmineTegevus.getNimi()
                + ".\nLohe teeb " + sissetulevKahju + " kahju.");

        uuendaHp();

        if (!kangelane.kasElus()) {
            lõpetaMäng(false);
            return;
        }

        kangelane.lõpetaKord();
        lohe.lõpetaKord();

        järgmineTegevus = lohe.valiTegevus();
        näitaJärgmineVoor();
    }

    /**
     * Kuvab "Järgmine voor" nupu.
     */
    private void näitaJärgmineVoor() {
        valikuteKast.getChildren().clear();

        Button jätka = new Button("(Enter) Järgmine voor");
        seadistaNupp(jätka);

        jätka.setOnAction(e -> {
            voorudeArv++;
            kirjutaTekst("Vihje: " + järgmineTegevus.getVihje());
            näitaPõhiValikud();
        });

        valikuteKast.getChildren().add(jätka);
    }

    /**
     * Lõpetab mängu (võit või kaotus).
     */
    private void lõpetaMäng(boolean võit) {
        valikuteKast.getChildren().clear();

        LocalDateTime mängulõpuAeg = LocalDateTime.now();
        TulemuseKirjutamine.kirjutaTulemus(kangelane, võit, voorudeArv, mänguAlguseAeg, mängulõpuAeg);

        if (võit) {
            kirjutaTekst(
                    "Palju õnne. Alistasid lohe ja printsess on päästetud.\n\n" +
                    "Tulemus salvestati faili mangu_tulemused.txt.\n" +
                    "Voorude arv: " + voorudeArv + "\n"
            );
        } else {
            kirjutaTekst(
                    "Lohe osutus liiga tugevaks. Sinu teekond lõppes siin.\n\n" +
                    "Tulemus salvestati faili mangu_tulemused.txt.\n" +
                    "Voorude arv: " + voorudeArv + "\n"
            );
        }

        Button menüü = new Button("(1) Peamenüü");
        seadistaNupp(menüü);
        menüü.setOnAction(e -> näitaPeamenüü());

        Button välju = new Button("(2) Välju");
        seadistaNupp(välju);
        välju.setOnAction(e -> stage.close());

        valikuteKast.getChildren().addAll(menüü, välju);
    }

    // -------------------------------------------------------------------------
    // Abimeetodid
    // -------------------------------------------------------------------------

    private void uuendaHp() {
        kangelaseHpLabel.setText(
                kangelane.getNimi() + " HP: " + kangelane.getHp() + "/" + kangelane.getMaxHp());

        int loheHpKokku =
                lohe.getPea().getHp()
                + lohe.getTorso().getHp()
                + lohe.getVasakTiib().getHp()
                + lohe.getParemTiib().getHp()
                + lohe.getVasakJalg().getHp()
                + lohe.getParemJalg().getHp();

        loheHpLabel.setText("Lohe HP kokku: " + loheHpKokku);
    }

    private void lisaKlaviatuurigaJuhtimine(Scene scene) {
        scene.setOnKeyPressed(e -> {
            if (valikuteKast == null) {
                return;
            }

            if (e.getCode() == KeyCode.DIGIT1) {
                vajutaNuppu(0);
            } else if (e.getCode() == KeyCode.DIGIT2) {
                vajutaNuppu(1);
            } else if (e.getCode() == KeyCode.DIGIT3) {
                vajutaNuppu(2);
            } else if (e.getCode() == KeyCode.DIGIT4) {
                vajutaNuppu(3);
            } else if (e.getCode() == KeyCode.DIGIT5) {
                vajutaNuppu(4);
            } else if (e.getCode() == KeyCode.DIGIT6) {
                vajutaNuppu(5);
            } else if (e.getCode() == KeyCode.ENTER) {
                vajutaNuppu(0);
            } else if (e.getCode() == KeyCode.ESCAPE) {
                vajutaViimastNuppu();
            }
        });
    }

    private void vajutaNuppu(int indeks) {
        if (valikuteKast == null) {
            return;
        }

        if (indeks < 0 || indeks >= valikuteKast.getChildren().size()) {
            return;
        }

        if (valikuteKast.getChildren().get(indeks) instanceof Button nupp) {
            nupp.fire();
        }
    }

    private void vajutaViimastNuppu() {
        if (valikuteKast == null) {
            return;
        }

        int viimaneIndeks = valikuteKast.getChildren().size() - 1;

        if (valikuteKast.getChildren().get(viimaneIndeks) instanceof Button nupp) {
            nupp.fire();
        }
    }


    private void kirjutaTekst(String tekst) {
        tekstiKast.setText(tekst);
    }

    private void seadistaNupp(Button nupp) {
        nupp.setMaxWidth(Double.MAX_VALUE);
        nupp.setPrefHeight(35);
    }
}
