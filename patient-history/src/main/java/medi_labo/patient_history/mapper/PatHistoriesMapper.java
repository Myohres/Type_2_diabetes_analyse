package medi_labo.patient_history.mapper;

import medi_labo.patient_history.model.DTO.PatHistoriesDTO;
import medi_labo.patient_history.model.DTO.PatHistoriesNoteDTO;
import medi_labo.patient_history.model.entity.PatHistory;

import java.util.List;

public class PatHistoriesMapper {

    public PatHistoriesDTO patHistoryToPatHistoriesDTO(List<PatHistory> patHistoryList) {
        PatHistoriesDTO patHistoriesDTO = new PatHistoriesDTO();
        patHistoriesDTO.setPatId(patHistoryList.get(0).getPatId());
        patHistoriesDTO.setPatient(patHistoryList.get(0).getPatient());
        List<PatHistoriesNoteDTO> patHistoriesNoteDTOList =
        patHistoryList.stream().map(
                this::patHistoryToPatHistoriesNoteDTO
        ).toList();
        patHistoriesDTO.setNoteListHistories(patHistoriesNoteDTOList);
        return patHistoriesDTO;
    }

    private PatHistoriesNoteDTO patHistoryToPatHistoriesNoteDTO(PatHistory patHistory){
        PatHistoriesNoteDTO patHistoriesNoteDTO = new PatHistoriesNoteDTO();
        patHistoriesNoteDTO.setId(patHistory.getId());
        patHistoriesNoteDTO.setNote(patHistory.getNote());
        return patHistoriesNoteDTO;
    }
}
