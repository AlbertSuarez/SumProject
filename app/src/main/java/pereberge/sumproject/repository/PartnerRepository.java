package pereberge.sumproject.repository;

import android.content.Context;
import com.firebase.client.DataSnapshot;
import pereberge.sumproject.domain.Partner;

public class PartnerRepository extends FirebaseRepository<Partner> {

    public PartnerRepository(Context context) {
        super(context);
    }

    @Override
    protected Partner convert(DataSnapshot data) {
        if (data == null) return null;

        Partner partner = new Partner();
        partner.setId(data.getKey());
        for (DataSnapshot d : data.getChildren()) {
            if (d.getKey().equals("name")) {
                partner.setName(d.getValue(String.class));
            }
        }
        return partner;
    }

    @Override
    public String getObjectReference() {
        return "partners";
    }
}
