package edu.asoldatov.nir.service.statistics;

import edu.asoldatov.nir.model.cadet.Tooth;
import lombok.Getter;

import java.util.List;

@Getter
public class CPU {

    private int intact;
    private int caries;
    private int fillingWithCaries;
    private int fillingWithoutCaries;
    private int removalCaries;
    private int removalOtherReason;
    private int sealedFissure;
    private int veneer;
    private int impacted;
    private int notRegistered;
    private int intactTemp;
    private int cariesTemp;
    private int fillingWithCariesTemp;
    private int fillingWithoutCariesTemp;
    private int removalCariesTemp;
    private int sealedFissureTemp;
    private int veneerTemp;

    //А		=		0 = интактный
    //
    //B		=		1 = кариес
    //
    //C		=		2 = пломба,с кариесом
    //
    //D		=		3 = пломба, без кариеса
    //
    //E		=		4 = удаление из-за осложнений кариеса
    //
    //–		=		5 = удаление по другим причинам
    //
    //F		=		6 = герметизированная фиссура
    //
    //G		=		7 = опорный зуб мостовидн. протез/коронка, винир
    //
    //–		=		8 = непрорезавшийся зуб
    //
    //–		=		9 = не регистрируется

    private CPU(int intact,
                int caries,
                int fillingWithCaries,
                int fillingWithoutCaries,
                int removalCaries,
                int removalOtherReason,
                int sealedFissure,
                int veneer,
                int impacted,
                int notRegistered,
                int intactTemp,
                int cariesTemp,
                int fillingWithCariesTemp,
                int fillingWithoutCariesTemp,
                int removalCariesTemp,
                int sealedFissureTemp,
                int veneerTemp) {
        this.intact = intact;
        this.caries = caries;
        this.fillingWithCaries = fillingWithCaries;
        this.fillingWithoutCaries = fillingWithoutCaries;
        this.removalCaries = removalCaries;
        this.removalOtherReason = removalOtherReason;
        this.sealedFissure = sealedFissure;
        this.veneer = veneer;
        this.impacted = impacted;
        this.notRegistered = notRegistered;
        this.intactTemp = intactTemp;
        this.cariesTemp = cariesTemp;
        this.fillingWithCariesTemp = fillingWithCariesTemp;
        this.fillingWithoutCariesTemp = fillingWithoutCariesTemp;
        this.removalCariesTemp = removalCariesTemp;
        this.sealedFissureTemp = sealedFissureTemp;
        this.veneerTemp = veneerTemp;
    }


    public CPU(List<Tooth> teeth) {
        for (Tooth tooth : teeth) {
            if (!isTemp(tooth)) {
                switch (tooth.getValue()) {
                    case 0: {
                        intact++;
                        break;
                    }
                    case 1: {
                        caries++;
                        break;
                    }
                    case 2: {
                        fillingWithCaries++;
                        break;
                    }
                    case 3: {
                        fillingWithoutCaries++;
                        break;
                    }
                    case 4: {
                        removalCaries++;
                        break;
                    }
                    case 5: {
                        removalOtherReason++;
                        break;
                    }
                    case 6: {
                        sealedFissure++;
                        break;
                    }
                    case 7: {
                        veneer++;
                        break;
                    }
                    case 8: {
                        impacted++;
                        break;
                    }
                    case 9: {
                        notRegistered++;
                        break;
                    }
                }
            } else {
                switch (tooth.getValue()) {
                    case 0: {
                        intactTemp++;
                        break;
                    }
                    case 1: {
                        cariesTemp++;
                        break;
                    }
                    case 2: {
                        fillingWithCariesTemp++;
                        break;
                    }
                    case 3: {
                        fillingWithoutCariesTemp++;
                        break;
                    }
                    case 4: {
                        removalCariesTemp++;
                        break;
                    }
                    case 6: {
                        sealedFissureTemp++;
                        break;
                    }
                    case 7: {
                        veneerTemp++;
                        break;
                    }
                }
            }
        }

    }

    public int K() {
        return (caries + fillingWithCaries);
    }

    public int P() {
        return (fillingWithoutCaries + sealedFissure);
    }

    public int U() {
        return (removalCaries + removalOtherReason + veneer);
    }

    public int k() {
        return (cariesTemp + fillingWithCariesTemp);
    }

    public int p() {
        return (fillingWithoutCariesTemp + sealedFissureTemp);
    }


    //КПУ=К(1,2)+П(3,6)+У(4,5,7)+кп=к(B,C)+п(D,F)
    public int calculateCpu() {
        return (caries + fillingWithCaries) +
                (fillingWithoutCaries + sealedFissure) +
                (removalCaries + removalOtherReason + veneer) +

                (cariesTemp + fillingWithCariesTemp) +
                (fillingWithoutCariesTemp + sealedFissureTemp);
    }

    private boolean isTemp(Tooth tooth) {
        return tooth.getNumber() > 47;
    }
}
