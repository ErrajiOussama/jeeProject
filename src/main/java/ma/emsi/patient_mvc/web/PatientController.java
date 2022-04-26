package ma.emsi.patient_mvc.web;

import lombok.AllArgsConstructor;
import ma.emsi.patient_mvc.entities.Patient;
import ma.emsi.patient_mvc.repositories.Patientreposetories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class PatientController {
  private Patientreposetories patientRepository;

  @GetMapping(path = "/user/index")
  public String patients(Model model,
                         @RequestParam(name = "page", defaultValue = "0") int page,
                         @RequestParam(name = "size", defaultValue = "15") int size,
                         @RequestParam(name = "keyword", defaultValue = "") String keyword){

    Page<Patient> pagePatients = patientRepository.findByNomContains(keyword,PageRequest.of(page, size));

    model.addAttribute("listPatients", pagePatients.getContent());
    model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
    model.addAttribute("currentPage", page);
    model.addAttribute("keyword", keyword);
    model.addAttribute("size", size);
    return "patients";
  }

  @GetMapping(path = "/admin/delete")
  public String delete(Long id, String keyword, int page, int size){
    patientRepository.deleteById(id);
    return "redirect:/user/index?page="+page+"&keyword="+keyword+"&size="+size;
  }

  @GetMapping(path = "/")
  public String home(){
    return "home";
  }

  @GetMapping(path = "/admin/formPatients")
  public String formPatients(Model model){
    model.addAttribute("patient", new Patient());
    return "formPatients";
  }

  @PostMapping (path = "/admin/save")
  public String save(Model model, @Valid Patient patient, BindingResult bindingResult){
    if(bindingResult.hasErrors()) return "formPatients";
    patientRepository.save(patient);
    return "redirect:/user/index";
  }

  @GetMapping(path = "/admin/editPatient")
  public String editPatient(Model model, Long id, String keyword,
                            int page, int size){
    Patient patient = patientRepository.findById(id).orElse(null);
    if(patient==null) throw new RuntimeException("Patient introuvable");
    model.addAttribute("patient", patient);
    model.addAttribute("keyword", keyword);
    model.addAttribute("page", page);
    model.addAttribute("size", size);
    return "editPatient";
  }

}
