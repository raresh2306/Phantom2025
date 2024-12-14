package org.firstinspires.ftc.teamcode.RobotParts;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Culisante {

    //todo deci pentru motoare si declararea lor avem 2 optiuni: primul tip
    // DcMotor (acesta se ocupa de controale simple precum viteza si pozitia
    // folosind un encoder(nu e foarte exact dar bun de folosit la roti)
    // al doilea tip se numeste DcMotorEx si e prescurtarea de la "DcMotorExtend"
    // ce face acest extend? ei bine ne lasa sa tunam si pidul fiecarui motor in
    // parte dar si sa controlm mai precis pozitia unui motor folosindu-ne de
    // tick-urile encoderelor

    Telemetry telemetry;
    public DcMotorEx CulisantaDreapta = null;
    public DcMotorEx CulisantaStanga = null;

    public Culisante(Telemetry telemetry){
        this.telemetry = telemetry;
    }

    public void Culisanteinit(HardwareMap map) {
        CulisantaDreapta = map.get(DcMotorEx.class, "CulisantaDreapta");
        CulisantaStanga = map.get(DcMotorEx.class, "CulisantaStanga");

        CulisantaStanga.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        CulisantaDreapta.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        //TODO: DE VERIFICAT SA NU FIE INVERS NEAPARAT
        //TODO: DE VERIFICAT SA NU FIE INVERS NEAPARAT
        CulisantaDreapta.setDirection(DcMotorSimple.Direction.FORWARD);
        CulisantaStanga.setDirection(DcMotorSimple.Direction.REVERSE);

        CulisantaStanga.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        CulisantaDreapta.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        CulisantaStanga.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        CulisantaDreapta.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        CulisantaDreapta.setPower(0);
        CulisantaStanga.setPower(0);

    }
    public int lastPostion = 0;
    public double lastPower = 0;


    //basically ce am facut si la servo cu enum si functie plus functie care duce culisanta la pozitie
    //int basket1=500, basket2, bara1, bara2; //todo modificare valori aici (1)

    public enum PozCulisante{
       Basket1, Basket2, Bara1, Bara2, Jos;
    }

    private int CulisanteNivel (PozCulisante poz) {
        switch (poz){
            case Jos: return 0;
            //todo sau aici (2)
            case Basket1: return 760;
            case Bara1: return 0;
            case Bara2: return 0;
            case Basket2: return 0;

            default: break;

        } return 0;
    }

    public void goToPosition (double power, PozCulisante poz){
        int pozitie = CulisanteNivel(poz);
        lastPower = power;
        lastPostion = pozitie;

        CulisantaStanga.setTargetPosition(pozitie);
        CulisantaDreapta.setTargetPosition(pozitie);

        CulisantaDreapta.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        CulisantaStanga.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        CulisantaStanga.setPower(power);
        CulisantaDreapta.setPower(power);

        telemetry.addData("Culisanta Stanga: ", CulisantaStanga.getCurrentPosition());
        telemetry.addData("Culisanta Dreapta: ", CulisantaDreapta.getCurrentPosition());
    }

    public void verificare(){
        CulisantaStanga.setTargetPosition(lastPostion);
        CulisantaDreapta.setTargetPosition(lastPostion);

        CulisantaStanga.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        CulisantaDreapta.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        CulisantaStanga.setPower(lastPower);
        CulisantaDreapta.setPower(lastPower);
    }

}
