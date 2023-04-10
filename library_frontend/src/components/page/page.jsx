import React, { useState, useEffect } from 'react';
import List from "/src/components/list/list";
import "./page.css";

function Page() {
    const urlCountry = "http://localhost:8090/people/getRandomCountry";
    const urlBook = "http://localhost:8090/bookrents/getTop3ReadBook?country_code="
    
    const [country, setCountry] = useState("");
    const [books, setBooks] = useState({});
    const [buttonClicked, setButtonClicked] = useState(false);
    
    const handleClick = () =>{
        fetch(urlCountry, {
            method: "GET",
            headers: {"Content-Type" : "application/json"}
        })
        .then((response) => response.json())
        .then(async (data) => {
            var url = urlBook + data[0].country.country_code;

            fetch(url, {
                        method: "GET",
                        headers: {"Content-Type" : "application/json"}})
            .then((response) => response.json())
            .then((data) => {
                                setBooks(data);                  
                            })
            .catch((error) => console.log(error));

            setCountry(data[0].country.country_code);
            setButtonClicked(true);    
        })
        .catch((error) => console.log(error));
    };

    useEffect(() => {
        console.log(books);
    }, [books]);

    return (
            <div>
                <button id="action-btn" onClick={handleClick}>
                    Get Country: {country}
                </button>
                {buttonClicked ? (
                    <List bookList={books}/>   
                ) : null}
            </div>
        );
}
 
export default Page;