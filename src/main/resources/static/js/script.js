let currentTheme = getTheme();

changeTheme();
function changeTheme(){
    document.querySelector("html").classList.add(currentTheme);
    // set the event listner to change the theme
    const themeChangeBtn = document.querySelector("#theme_change_btn");

    themeChangeBtn.querySelector("span").textContent = currentTheme == "light" ? "Dark" : "Light";

    themeChangeBtn.addEventListener("click",(event) =>{
        document.querySelector("html").classList.remove(currentTheme);

        if(currentTheme == "dark"){
            currentTheme = "light";
        }else{
            currentTheme = "dark";
        }

        setTheme(currentTheme);
        document.querySelector("html").classList.add(currentTheme);

        themeChangeBtn.querySelector("span").textContent = currentTheme == "light" ? "Dark" : "Light";

    })
}

// set theme to local storage
function setTheme(theme){
    localStorage.setItem("theme",theme);
}

// get theme from local storage
function getTheme(){
    let theme = localStorage.getItem("theme");

    if(theme){
        return theme;
    }else{
        return "light";
    }
}
