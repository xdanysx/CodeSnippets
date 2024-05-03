using UnityEngine;

// Dieses Script ermöglicht einem aus der Sicht der Kamera mit der Maus auf die GameObjects zu klicken
public class MouseHandler : MonoBehaviour
{
    [SerializeField] private Camera mainCamera;
    // Update is called once per frame
    void Update()
    {
        //Debug.Log(mainCamera.ScreenToWorldPoint(Input.mousePosition));
        //Debug.Log(Input.mousePosition);       
        
        Ray ray = mainCamera.ScreenPointToRay(Input.mousePosition);
        if(Physics.Raycast(ray, out RaycastHit raycastHit))
        {
            Debug.Log(raycastHit.transform.name);
        }

    }
}
