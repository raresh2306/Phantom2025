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
    public double transfer_init=0.04;
    public Servo pozTransfer = null;
    public double pozTransfer_init= 0;



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
        Fata, Vertical, Basket, Specimen;
    }

    //todo ce urmeaza aici e pur si simplu un case care are pozitiile servo urilor denumite simplu
    public double returnPos (pozAxoane pozitie) {
        switch (pozitie){
            case Fata: return 0.038;
            case Basket: return 0.4;
            case Specimen: return 0.65;
            case Vertical: return 0.3;

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
    public void extendo () throws InterruptedException {
        pozTransfer.setPosition(0);
        gripper.setPosition(0.85);
        AxonLaPozitie(pozAxoane.Vertical);
        sleep(200);
        extindere.setPosition(0.81);
        sleep(100);
        bratGripper.setPosition(0.846);
        sleep(300);
        gripper.setPosition(0.97);
    }
    public void transf() throws InterruptedException {
        rotireGripper.setPosition(rotiregripper_init);
        transfer.setPosition(0.375);
        sleep(50);
        bratGripper.setPosition(0.37);
        sleep(150);
        extindere.setPosition(0.96);
        sleep(500);
        AxonLaPozitie(Servos.pozAxoane.Fata);
        sleep(245);
        transfer.setPosition(0.05); sleep(200);
        decolectare();
        sleep(250);
        AxonLaPozitie(Servos.pozAxoane.Basket);
        pozTransfer.setPosition(0.5);
        gripper.setPosition(0.94);
    }

    public void colectare() {
        gripper.setPosition(0.825);
    }

    public void decolectare(){
        gripper.setPosition(0.97);
    }









}
