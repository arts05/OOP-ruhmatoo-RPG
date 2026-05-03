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

        Button edasi = new Button("Edasi");
        Button tagasi = new Button("Tagasi");

        edasi.setPrefWidth(180);
        tagasi.setPrefWidth(180);

        edasi.setOnAction(e -> näitaKlassiValik());
        tagasi.setOnAction(e -> näitaPeamenüü());

        HBox nupud = new HBox(15);
        nupud.setAlignment(Pos.CENTER);
        nupud.getChildren().addAll(tagasi, edasi);

        introPaneel.getChildren().addAll(pealkiri, introTekst, nupud);

        root.getChildren().add(introPaneel);

        stage.setScene(new Scene(root, 900, 600));
    }

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
                        "-fx-border-color: rgba(255, 255, 255, 0.75);" +
                        "-fx-border-width: 3;" +
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

        mängi.setOnAction(e -> näitaIntro());
        välju.setOnAction(e -> stage.close());

        menüüPaneel.getChildren().addAll(pealkiri, mängi, välju);
        root.getChildren().add(menüüPaneel);

        Scene scene = new Scene(root, 900, 600);
        stage.setScene(scene);
        stage.show();
    }

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

        Button warrior = new Button("Mõõgamees");
        Button archer = new Button("Vibumees");
        Button tagasi = new Button("Tagasi");

        warrior.setPrefWidth(220);
        archer.setPrefWidth(220);
        tagasi.setPrefWidth(220);

        warrior.setOnAction(e -> alustaMäng(new Warrior()));
        archer.setOnAction(e -> alustaMäng(new Archer()));
        tagasi.setOnAction(e -> näitaIntro());

        valikuPaneel.getChildren().addAll(tekst, warrior, archer, tagasi);

        root.getChildren().add(valikuPaneel);

        stage.setScene(new Scene(root, 900, 600));
    }

    private void alustaMäng(Kangelane valitudKangelane) {
        kangelane = valitudKangelane;
        lohe = new Lohe();
        järgmineTegevus = lohe.valiTegevus();

        looMänguVaade();

        kirjutaTekst("Oled jõudnud lohe kantsi. Lohe valmistub ründama.\nVihje: " + järgmineTegevus.getVihje());
        uuendaVaade("LOHE OOTAB");
        näitaPõhiValikud();
    }

    private void looMänguVaade() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #202020;");

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

        pildiPlaceholder = new Label("PLACEHOLDER");
        pildiPlaceholder.setAlignment(Pos.CENTER);
        pildiPlaceholder.setTextFill(Color.WHITE);
        pildiPlaceholder.setFont(Font.font(36));
        pildiPlaceholder.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        pildiPlaceholder.setStyle("-fx-background-color: #3a3a3a; -fx-border-color: white; -fx-border-width: 3;");

        StackPane keskmine = new StackPane(pildiPlaceholder);
        keskmine.setPadding(new Insets(10));

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

        stage.setScene(new Scene(root, 900, 600));
        uuendaHp();
    }

    private void näitaPõhiValikud() {
        valikuteKast.getChildren().clear();

        Button ründa = new Button("Ründa");
        Button peida = new Button("Peida end");
        Button välju = new Button("Välju");

        seadistaNupp(ründa);
        seadistaNupp(peida);
        seadistaNupp(välju);

        ründa.setOnAction(e -> näitaSihtmärgid());
        peida.setOnAction(e -> peidaKangelane());
        välju.setOnAction(e -> stage.close());

        valikuteKast.getChildren().addAll(ründa, peida, välju);
    }

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
        tagasi.setOnAction(e -> näitaPõhiValikud());

        valikuteKast.getChildren().add(tagasi);
    }

    private void lisaSihtmärgiNupp(String nimi, KehaOsa kehaOsa) {
        Button nupp = new Button(nimi + " (" + kehaOsa.getHp() + " HP)");
        seadistaNupp(nupp);
        nupp.setOnAction(e -> mängijaRündab(kehaOsa));
        valikuteKast.getChildren().add(nupp);
    }

    private void mängijaRündab(KehaOsa sihtmärk) {
        if (sihtmärk.hävitatud()) {
            kirjutaTekst("See kehaosa on juba hävitatud. Vali teine sihtmärk.");
            return;
        }

        Rünnak rünnak = (kangelane instanceof Warrior) ? Rünnak.mõõk() : Rünnak.vibu();

        int tabamus = kangelane.tabamusTõenäosus(rünnak, lohe, sihtmärk);
        int vise = Täring.veeretaProtsent();

        if (vise <= tabamus) {
            int damage = kangelane.teeHaiget(rünnak, lohe);
            sihtmärk.saaHaiget(damage);
            lohe.suurendaViha(2);

            uuendaVaade("KANGELANE RÜNDAB");

            String tekst = "Rünnak tabas.\nTegid " + sihtmärk.getNimiOmastavas() + " pihta " + damage + " kahju.\n";
            tekst += "Tabamustõenäosus: " + tabamus + "%, vise: " + vise + ".";

            if ((sihtmärk == lohe.getVasakJalg() || sihtmärk == lohe.getParemJalg()) && sihtmärk.hävitatud()) {
                lohe.setSunniLeegigaRünnak(true);
                tekst += "\nLohe jalg hävis. Lohe vihastab ja valmistub leeki heitma.";
            }

            if ((sihtmärk == lohe.getVasakTiib() || sihtmärk == lohe.getParemTiib()) && sihtmärk.hävitatud()) {
                if (!lohe.saabLennata()) {
                    lohe.setLendab(false);
                    tekst += "\nLohe tiib hävis. Lohe ei saa enam lennata.";
                }
            }

            kirjutaTekst(tekst);
        } else {
            uuendaVaade("KANGELANE RÜNDAB");
            kirjutaTekst("Rünnak läks mööda.\nTabamustõenäosus: " + tabamus + "%, vise: " + vise + ".");
        }

        uuendaHp();

        if (!lohe.kasElus()) {
            lõpetaMäng(true);
            return;
        }

        näitaJätkaLoheKäigule();
    }

    private void peidaKangelane() {
        kangelane.setPeidus(true);
        uuendaVaade("KANGELANE PEIDUS");
        kirjutaTekst(kangelane.getNimi() + " peidab end kivide taha.");
        näitaJätkaLoheKäigule();
    }

    private void näitaJätkaLoheKäigule() {
        valikuteKast.getChildren().clear();

        Button jätka = new Button("Jätka");
        seadistaNupp(jätka);
        jätka.setOnAction(e -> loheRündab());

        valikuteKast.getChildren().add(jätka);
    }

    private void loheRündab() {
        int sissetulevKahju = lohe.veeretaRünnakuDamage(järgmineTegevus);

        if (kangelane.isPeidus()) {
            Kaitse kaitse = new Kaitse();
            sissetulevKahju = kaitse.blokeeriKahju(kangelane, sissetulevKahju);
        }

        kangelane.saaHaavata(sissetulevKahju);

        if (järgmineTegevus == LoheTegevus.heidaLeeki()) {
            uuendaVaade("LOHE HEIDAB LEEKI");
        } else if (järgmineTegevus == LoheTegevus.ründaSabaga()) {
            uuendaVaade("LOHE RÜNDAB SABAGA");
        } else if (järgmineTegevus == LoheTegevus.ründaJalaga()) {
            uuendaVaade("LOHE RÜNDAB JALAGA");
        }

        kirjutaTekst("Lohe tegevus: " + järgmineTegevus.getNimi() + ".\nLohe teeb " + sissetulevKahju + " kahju.");

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

    private void näitaJärgmineVoor() {
        valikuteKast.getChildren().clear();

        Button jätka = new Button("Järgmine voor");
        seadistaNupp(jätka);

        jätka.setOnAction(e -> {
            uuendaVaade(lohe.lendab() ? "LOHE LENDAB" : "LOHE MAAS");
            kirjutaTekst("Vihje: " + järgmineTegevus.getVihje());
            näitaPõhiValikud();
        });

        valikuteKast.getChildren().add(jätka);
    }

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
        menüü.setOnAction(e -> näitaPeamenüü());

        Button välju = new Button("Välju");
        seadistaNupp(välju);
        välju.setOnAction(e -> stage.close());

        valikuteKast.getChildren().addAll(menüü, välju);
    }

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

    private void uuendaVaade(String tekst) {
        pildiPlaceholder.setText(tekst);
    }

    private void kirjutaTekst(String tekst) {
        tekstiKast.setText(tekst);
    }

    private void seadistaNupp(Button nupp) {
        nupp.setMaxWidth(Double.MAX_VALUE);
        nupp.setPrefHeight(35);
    }
}