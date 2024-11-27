package net.chabab.utilisateurservice.mappers;
import net.chabab.utilisateurservice.dtos.UtilisateurDTO;
import net.chabab.utilisateurservice.entities.Utilisateur;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UtilisateurMapper {
    UtilisateurMapper INSTANCE = Mappers.getMapper(UtilisateurMapper.class);

    UtilisateurDTO toDto(Utilisateur utilisateur);
    Utilisateur toEntity(UtilisateurDTO utilisateurDTO);
}
