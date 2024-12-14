package org.firstinspires.ftc.teamcode.RobotParts;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class Servos {       /* ToDo face textul verde sa va sara in ochi */
    //  daca declarati ceva public, puteti sa accesati elementul(obiectul)
    //  respectiv de oriunde din proiectul vostru doar dupa nume
    //  insa daca il declarati private, acesta devine inaccesibil din
    // alte clase dupa numele sau.

    //Exemplu: putem avea private int numar; (variabila numar poate fi accesata doar din
    // interiorul clasei in care este declarata, in momentul acesta clasa este "Servos")
    // daca vrem sa accesam totusi de altundeva fara a schimba vizibilitatea sa, va trebui sa ne
    // folosim de o functie de tipul public int aflaNumar() {return numar;}
    public Servo extindere = null;
    /** float are dimensiunea de 32 bits (double  64 prin comparatie)
     *  ia valori pana la 7 decimale (double pana la 16)*/
    public double extindere_init =0.99; /* Todo PUNE POZITII LA TOT (adica pui egal si valoarea dupa) */
    public Servo gripper = null;
    public double gripper_init = 0.97;
    public Servo rotireGripper = null;
    public double rotiregripper_init = 0.25; //Va recomand sa nu denumiti partile robotului cu "_",
    // ci cu Majuscula intre cuvinte (arata mai bine si e mai usor de "call-uit")
    // TODO in schimb, cand dati nume unor variabile ce iau valori (de pozitii) merg cu underscore
    public Servo bratGripper = null;
    public double bratGrippper_init=0.37;
    public Servo AxonStanga = null;
    public Servo AxonDreapta = null;
    public double AxoaneRotire_init = 0;
    public Servo transfer = null;
    public double transfer_init=0.5;
    public Servo pozTransfer = null;
    public double pozTransfer_init= 0.25;



    public Servos() { //asta se numeste constructor, fiecare clasa ce contine init
        // are nevoie de un constructor pentru a putea sa initializam servo / motoare etc.
        /** The Servos() constructor is a default constructor.
         *  Its purpose is to allow the creation of instances of the Servo class
         *  without requiring any specific initialization logic. (ma bate romana la ora asta)*/
    }

    public void initServo(HardwareMap map) throws InterruptedException { //aici declaram numele (din config) al fiecarui
        // servomotor in parte dar si pozitia sa din initializare (va voi lasa semne unde
        // trebuie sa le ppuneti voi TODO Later Edit: Majoritatea functiilor predefinite
        //  TODO sunt usor de inteles nu le voi explica aici

        rotireGripper = map.get(Servo.class, "rotireGripper");
        rotireGripper.setPosition(rotiregripper_init);
        sleep(50);

        bratGripper = map.get(Servo.class, "bratGripper");
        bratGripper.setPosition(bratGrippper_init);
        sleep(50);

        extindere = map.get(Servo.class, "extinere");
        extindere.setPosition(extindere_init);
        sleep(50);

        gripper = map.get(Servo.class, "gripper");
        gripper.setPosition(gripper_init);

        transfer = map.get(Servo.class, "transfer");
        transfer.setPosition(transfer_init);

        pozTransfer = map.get(Servo.class, "pozGripper");
        pozTransfer.setPosition(pozTransfer_init);
        sleep(300);




    }
    public void initAxoane(HardwareMap map) throws InterruptedException {
        AxonDreapta = map.get(Servo.class, "AxonDreapta");
        AxonStanga = map.get(Servo.class, "AxonStanga");
        AxonDreapta.setPosition(AxoaneRotire_init);
        AxonStanga.setPosition(AxoaneRotire_init);
    }

    //TODO: Aici faceti voi functii necesare pentru fiecare miscare
    //TODO: o sa va fac una de exemplu si dupa va faceti voi si pentru restul


    //va mai invat ceva daca tot, pentru un servo care are mai multe pozitii, puteti face
    // cate o variabila pentru fiecare pozitie, dar mai muteti face ceva (se aplica si la motoare)
    // actually, e mai indicat la motoare dar daca tot suntem aici why not
    // So, vom folosi un enum (enumerare) si un switch case

    public enum pozAxoane {
        Fata, Vertical, Basket, Specimen, Putinsus;
    }

    //todo ce urmeaza aici e pur si simplu un case care are pozitiile servo urilor denumite simplu
    public double returnPos (pozAxoane pozitie) {
        switch (pozitie){
            case Putinsus: return 0.1375; //175
            case Fata: return 0.042;
            case Basket: return 0.4;
            case Specimen: return 0.45;
            case Vertical: return 0.35;

            default: break;
        }
        return 0;
    }

        /** Aici avem o functie numita "AxonLaPozitie" care primeste ca argument acelasi ca la
         * functia de switch si anume pozAxoane si pozitie*/

        //in interiorul sau, functia are o variabila de tip double(numar real) ce citeste
        // pozitia luata din switch
    public void AxonLaPozitie (pozAxoane pozitie) {
         double pozitiune = returnPos(pozitie);
        AxonDreapta.setPosition(pozitiune);
        AxonStanga.setPosition(pozitiune);
    }

    //TODO o functie pentru servo

    //acum poti apela functia extendo iar extinderea se duce la pozitia sa maxima pentru colectare


    private enum SpecimenState{IDLE, START, SET_AXON, FINALIZE}

    private SpecimenState specimenState = SpecimenState.IDLE;
    private long specimenStartTime = 0;
    public void startSpecimen() {
        specimenState = SpecimenState.START;
        specimenStartTime = System.currentTimeMillis();
    }

    public void updateSpecimen(){
        if(specimenState== SpecimenState.IDLE) return;

        long elapsedTime = System.currentTimeMillis() - specimenStartTime;

        switch (specimenState) {

            case START:
                extindere.setPosition(extindere_init);
                pozTransfer.setPosition(0.55);
                gripper.setPosition(0.85);
                specimenStartTime = System.currentTimeMillis();
                specimenState = SpecimenState.SET_AXON;
                break;

            case SET_AXON:
                if (elapsedTime>100) {
                    AxonLaPozitie(pozAxoane.Specimen);
                    specimenStartTime = System.currentTimeMillis();
                    specimenState = SpecimenState.FINALIZE;
                }
                    break;


            case FINALIZE:
                if (elapsedTime>100) {
                    transfer.setPosition(0.325);
                    specimenStartTime = System.currentTimeMillis();
                    specimenState = SpecimenState.IDLE;
                }
                break;

        }

    }

    private enum ExtendoState {
        IDLE, START, SET_GRIPPER, SET_AXON, EXTEND, MOVE_BRAT, FINALIZE
    }

    private ExtendoState extendoState = ExtendoState.IDLE;
    private long extendoStartTime = 0;

    public void startExtendo() {
        extendoState = ExtendoState.START;
        extendoStartTime = System.currentTimeMillis();
    }

    public void updateExtendo() {
        if (extendoState == ExtendoState.IDLE) return;

        long elapsedTime = System.currentTimeMillis() - extendoStartTime;

        switch (extendoState) {
            case START:
                pozTransfer.setPosition(0.25);
                gripper.setPosition(0.85);
                AxonLaPozitie(pozAxoane.Putinsus);
                extendoStartTime = System.currentTimeMillis();
                extendoState = ExtendoState.EXTEND;
                break;

//            case SET_AXON:
//                if (elapsedTime > 200) {
//                    AxonLaPozitie(pozAxoane.Vertical);
//                    extendoStartTime = System.currentTimeMillis();
//                    extendoState = ExtendoState.EXTEND;
//                }
//                break;

            case EXTEND:
                if (elapsedTime > 300) {
                    extindere.setPosition(0.81);
                    extendoStartTime = System.currentTimeMillis();
                    extendoState = ExtendoState.MOVE_BRAT;
                }
                break;

            case MOVE_BRAT:
                if (elapsedTime > 300) {
                    bratGripper.setPosition(0.846);
                    extendoStartTime = System.currentTimeMillis();
                    extendoState = ExtendoState.FINALIZE;
                }
                break;

            case FINALIZE:
                if (elapsedTime > 100) {
                    gripper.setPosition(0.97);
                    pozTransfer.setPosition(0.25);
                    extendoState = ExtendoState.IDLE;
                }
                break;
        }
    }

        private enum TransfState {
            IDLE, START, AUX, MOVE_BRAT, EXTEND, AXON_FRONT, DECOLECTARE, FINALIZE
        }

        private TransfState transfState = TransfState.IDLE;
        private long transfStartTime = 0;

        public void startTransf() {
            transfState = TransfState.START;
            transfStartTime = System.currentTimeMillis();
        }

        public void updateTransf() {
            if (transfState == TransfState.IDLE) return;

            long elapsedTime = System.currentTimeMillis() - transfStartTime;

            switch (transfState) {
                case START:
                    colectare();
                    rotireGripper.setPosition(rotiregripper_init);
                    transfer.setPosition(0.325);
                    transfStartTime = System.currentTimeMillis();
                    transfState = TransfState.MOVE_BRAT;
                    break;

                case MOVE_BRAT:
                    if (elapsedTime > 25) {
                        bratGripper.setPosition(0.37);
                        transfStartTime = System.currentTimeMillis();
                        transfState = TransfState.EXTEND;
                    }
                    break;

                case EXTEND:
                    if (elapsedTime > 250) {
                        extindere.setPosition(0.925);
                        transfStartTime = System.currentTimeMillis();
                        transfState = TransfState.AXON_FRONT;
                    }
                    break;

                case AXON_FRONT:
                    if (elapsedTime > 300) {
                        pozTransfer.setPosition(0);
                        transfStartTime = System.currentTimeMillis();
                        transfState = TransfState.AUX;
                    }
                    break;

                case AUX:
                    if(elapsedTime >100) {
                        AxonLaPozitie(pozAxoane.Fata);
                        transfStartTime = System.currentTimeMillis();
                        transfState = TransfState.DECOLECTARE;
                    }

                case DECOLECTARE:
                    if (elapsedTime > 350) { //390
                        transfer.setPosition(0.05);
                        decolectare();
                        transfStartTime = System.currentTimeMillis();
                        transfState = TransfState.FINALIZE;
                    }
                    break;

                case FINALIZE:
                    if (elapsedTime > 75) {
                        AxonLaPozitie(Servos.pozAxoane.Basket);
                        pozTransfer.setPosition(0.5);
                        gripper.setPosition(0.94);
                        transfState = TransfState.IDLE; // Operation is complete
                    }
                    break;
            }
        }

    public void colectare() {
        gripper.setPosition(0.75);
    }

    public void decolectare(){
        gripper.setPosition(0.97);
    }









}
