package com.finalProyect.CynthiaLabrador.composition.services;

import com.finalProyect.CynthiaLabrador.champions.model.Champion;
import com.finalProyect.CynthiaLabrador.champions.services.ChampionsService;
import com.finalProyect.CynthiaLabrador.composition.dto.CreateCompositionDto;
import com.finalProyect.CynthiaLabrador.composition.model.Composition;
import com.finalProyect.CynthiaLabrador.composition.repository.CompositionRepository;
import com.finalProyect.CynthiaLabrador.file.services.base.BaseService;
import com.finalProyect.CynthiaLabrador.users.model.UserEntity;
import com.finalProyect.CynthiaLabrador.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompositionService extends BaseService<Composition, UUID, CompositionRepository> {

    private final CompositionRepository compositionRepository;
    private final UserEntityService userService;
    private final ChampionsService championsService;

    public List<Composition> getAllCompositionByUser(String nick) {
        UserEntity user = userService.findByNick(nick);
        return compositionRepository.findAllByAuthor(user).orElseThrow(() -> new RuntimeException("This user doesn't have compositions"));
    }

    public Composition getCompositionById(UUID id) {
        return compositionRepository.findById(id).orElseThrow(() -> new RuntimeException("This composition doesn't exist"));
    }

    public List<Composition> getAllComposition(){
        return compositionRepository.findAll();
    }

    public Composition createComposition(CreateCompositionDto createCompositionDto, UserEntity user) {
        List<Champion> champions = createCompositionDto.getChampions().stream().map(championsService::findFirstByName).collect(Collectors.toList());

        Composition composition = new Composition();
        composition.setName(createCompositionDto.getName());
        composition.setDescription(createCompositionDto.getDescription());
        composition.setChampions(champions);
        composition.setAuthor(user);
        composition.setCreateTime(LocalDateTime.now());
        return compositionRepository.save(composition);
    }

    public Composition updateComposition(Composition composition, UUID id, UserEntity user) {

        Composition compositionToUpdate = getCompositionById(id);
        if(compositionToUpdate.getAuthor().getNick().equals(user.getNick())) {
            compositionToUpdate.setName(composition.getName());
            compositionToUpdate.setDescription(composition.getDescription());
            compositionToUpdate.setChampions(composition.getChampions());
            return compositionRepository.save(compositionToUpdate);
        } else {
            return null;
        }
    }

    public void deleteComposition(UUID id, UserEntity user) {
        Composition compositionToDelete = getCompositionById(id);
        if(compositionToDelete.getAuthor().getNick().equals(user.getNick())) {
            compositionRepository.deleteById(id);
        }
    }

    public void addVote(UUID id, UserEntity user) {
        Composition compositionToUpdate = getCompositionById(id);

        if(!compositionToUpdate.getVotes().contains(user.getNick())) {
            compositionToUpdate.getVotes().add(user.getNick());
            compositionRepository.save(compositionToUpdate);
        }
    }

    public void removeVote(UUID id, UserEntity user) {
        Composition compositionToUpdate = getCompositionById(id);
        if(compositionToUpdate.getVotes().contains(user.getNick())) {
            compositionToUpdate.getVotes().remove(user.getNick());
            compositionRepository.save(compositionToUpdate);
        }
    }
}
