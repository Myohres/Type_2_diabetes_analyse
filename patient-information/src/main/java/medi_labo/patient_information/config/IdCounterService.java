package medi_labo.patient_information.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;



@Service
public class IdCounterService {

    private final MongoOperations mongoOperations;

    @Autowired
    public IdCounterService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public int generateCounter(String name) {
        Query query = new Query(Criteria.where("_id").is(name));
        Update update = new Update().inc("sequence", 1);
        FindAndModifyOptions options = FindAndModifyOptions.options().returnNew(true).upsert(true);
        IdCounter counter = mongoOperations.findAndModify(query, update, options, IdCounter.class);
        return counter != null ? counter.getSequence() : 1;
    }
}
